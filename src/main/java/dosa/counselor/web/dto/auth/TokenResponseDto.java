package dosa.counselor.web.dto.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenResponseDto {

    String accessToken;
    String refreshToken;

    @Builder
    public TokenResponseDto(String accessToken, String refreshToken){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
