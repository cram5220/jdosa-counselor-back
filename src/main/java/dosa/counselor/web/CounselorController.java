package dosa.counselor.web;

import dosa.counselor.config.auth.LoginUser;
import dosa.counselor.config.auth.dto.AuthedUser;
import dosa.counselor.service.user.UserService;
import dosa.counselor.web.dto.counselor.ChangePwRequestDto;
import dosa.counselor.web.dto.counselor.CounselorResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CounselorController {

    private final UserService userService;

    // 상담사 정보 조회
    @GetMapping("/counselor")
    public CounselorResponseDto getCounselor(@LoginUser AuthedUser authedUser){
        return userService.findByUserIdx(authedUser.getIdx());
    }

    // 비밀번호 변경
    @PutMapping("/counselor/password")
    public void updateCounselorPW(@LoginUser AuthedUser user,
                                  @RequestBody ChangePwRequestDto dto){
        userService.updateCounselorPW(user.getEmail(), dto);
    }

}
