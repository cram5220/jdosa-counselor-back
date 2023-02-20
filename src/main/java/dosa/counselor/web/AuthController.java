package dosa.counselor.web;

import dosa.counselor.config.auth.jwt.JwtTokenProvider;
import dosa.counselor.domain.user.Role;
import dosa.counselor.domain.user.User;
import dosa.counselor.service.user.UserService;
import dosa.counselor.web.dto.auth.LoginRequestDto;
import dosa.counselor.web.dto.auth.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/auth/loginprocess")
    public TokenResponseDto loginprocess(@RequestBody LoginRequestDto dto){

        // 이메일과 패스워드로 회원 조회
        User user = userService.findByLoginInfo(dto.getEmail(), dto.getPassword());
        // 토큰 발급
        String accessToken = jwtTokenProvider.createToken(user.getIdx(), Role.COUNSELOR);
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getIdx());

        return new TokenResponseDto(accessToken, refreshToken);
    }

    // 토큰 갱신
    @PostMapping("/auth/tokenRefresh")
    public TokenResponseDto tokenRefresh(@RequestBody TokenResponseDto dto){
        return jwtTokenProvider.refreshTokens(dto.getRefreshToken());
    }



}
