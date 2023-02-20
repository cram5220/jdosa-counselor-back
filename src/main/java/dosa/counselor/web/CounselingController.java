package dosa.counselor.web;

import dosa.counselor.common.CommonUtil;
import dosa.counselor.common.DataConverter;
import dosa.counselor.common.exception.BadCredentialsException;
import dosa.counselor.common.exception.ErrorCode;
import dosa.counselor.common.exception.IllegalArgumentException;
import dosa.counselor.config.auth.LoginUser;
import dosa.counselor.config.auth.dto.AuthedUser;
import dosa.counselor.config.auth.jwt.JwtTokenProvider;
import dosa.counselor.domain.counseling.CounselingView;
import dosa.counselor.domain.user.Role;
import dosa.counselor.service.auth.AuthService;
import dosa.counselor.service.counseling.CounselingService;
import dosa.counselor.web.dto.auth.TokenCounselingResponseDto;
import dosa.counselor.web.dto.counseling.CancelRequestDto;
import dosa.counselor.web.dto.counseling.CounselingResponseDto;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.util.List;


/*
 * 상담 정보 컨트롤러
 * */
@RequiredArgsConstructor
@RestController
public class CounselingController {

    private final CounselingService counselingService;
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;


    @Value("${frontend-url}")
    private String frontUrl;


    /*
     * 단일 조회 : 최근
     * */
    @GetMapping("/counseling")
    public CounselingResponseDto read(@LoginUser AuthedUser user,
                       @RequestParam("statuses") Short[] statuses)
    {
        return counselingService.findTop1ByCounselorIdxAndStatusInOrderByCounselingDatetime(user.getIdx(),statuses);
    }


    ///====== 아래는 실제로 필요

    @GetMapping("/counseling/listByDate")
    public List<CounselingResponseDto> list(@LoginUser AuthedUser user,
                                            @RequestParam("date") String date){

        return counselingService.findListByDate(user.getIdx(), DataConverter.stringToDate(date));
    }


    /*
     * 단일 조회 : 인덱스
     * idx : 상담 고유 번호
     * */
    @GetMapping("/counseling/{idx}")
    public CounselingResponseDto read(@LoginUser AuthedUser user, @PathVariable Long idx)
    {
        return counselingService.findById(idx);
    }


    /*
     * 목록 조회
     * statuses : ['ongoing','cancel'[, ...]]
     * */
    @GetMapping("/counseling/list")
    public Page<CounselingView> list(@LoginUser AuthedUser user,
                                     @RequestParam("statuses") Short[] statuses,
                                     @PageableDefault(sort = "counselingDate",  direction = Sort.Direction.DESC) Pageable pageable){

        return counselingService.findAllByCounselorIdxAndStatusIn(user.getIdx(), statuses, pageable);
    }


    /*
     * 예약 취소
     * */
    @PutMapping("/counseling/cancel")
    public void cancelCounseling(@LoginUser AuthedUser user,
                                   @RequestBody CancelRequestDto dto)
    {
        counselingService.cancelCounseling(user.getIdx(), dto.getCounselingIdx(), dto.getCancelReason());
    }



    /*
     * 코드 입장
     * */
    @PostMapping("/counseling/directEnter")
    public TokenCounselingResponseDto enterByCode(@RequestBody JSONObject dto)
    {

        try{
            // 코드 확인
            String[] codeSplit = CommonUtil.splitEncryptedCode(dto.getAsString("code"));
            Boolean isValidCode = authService.validateAuthcode(Long.valueOf(codeSplit[0]), (short) 3, URLEncoder.encode(dto.getAsString("code")));

            if(!isValidCode){
                throw new IllegalArgumentException(ErrorCode.ILLEGAL_ARGUMENT);
            }

            // 상담방 정보 불러와서
            CounselingResponseDto counseling = counselingService.findById(Long.valueOf(codeSplit[1]));


            if(counseling.getCounselorIdx().equals(Long.valueOf(codeSplit[0]))){ // 상담사번호와 상담번호가 일치하면
                // 토큰 발급
                String accessToken = jwtTokenProvider.createToken(counseling.getCounselorIdx(), Role.COUNSELOR);
                String refreshToken = jwtTokenProvider.createRefreshToken(counseling.getCounselorIdx());
                // 토큰 반환
                return new TokenCounselingResponseDto(accessToken, refreshToken, counseling.getIdx());
            }else{
                throw new BadCredentialsException(ErrorCode.BAD_CREDENTIALS);
            }

        } catch (Exception e) {
            throw new IllegalArgumentException(ErrorCode.ILLEGAL_ARGUMENT);
        }


    }


}
