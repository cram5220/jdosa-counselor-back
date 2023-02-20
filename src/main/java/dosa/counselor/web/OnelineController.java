package dosa.counselor.web;

import dosa.counselor.common.exception.CustomException;
import dosa.counselor.common.exception.ErrorCode;
import dosa.counselor.config.auth.LoginUser;
import dosa.counselor.config.auth.dto.AuthedUser;
import dosa.counselor.domain.oneline.Oneline;
import dosa.counselor.service.oneline.OnelineService;
import dosa.counselor.web.dto.oneline.OnelineModifyRequestDto;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/*
 * 한줄 상담 컨트롤러
 * */
@RequiredArgsConstructor
@RestController
public class OnelineController {

    private final OnelineService onelineService;

    /*
     * 한줄 상담 조회 (미답변 및 내 답변 or 내 답변)
     * 페이징 조회, 최신순
     * */
    @GetMapping("/oneline/list")
    public Page<Oneline> list(@LoginUser AuthedUser user,
                              @PageableDefault(sort = "createdDate",  direction = Sort.Direction.DESC) Pageable pageable){
        return onelineService.findAllReplyAvailable(user.getIdx(), pageable);
    }

    /*
     * 한줄 상담 답변 등록
     * */
    @PutMapping("/oneline/regist")
    public void regist(@LoginUser AuthedUser user,
                         @RequestBody OnelineModifyRequestDto dto){
        try{
            onelineService.registReplyBody(user.getIdx(), dto);
        }catch (Exception e){
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }


}
