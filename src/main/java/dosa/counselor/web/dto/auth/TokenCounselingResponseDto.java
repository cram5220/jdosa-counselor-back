package dosa.counselor.web.dto.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenCounselingResponseDto extends TokenResponseDto{

    Long counselingIdx;

    public TokenCounselingResponseDto(String accessToken, String refreshToken, Long counselingIdx){
        super(accessToken, refreshToken);
        this.counselingIdx = counselingIdx;
    }

}
