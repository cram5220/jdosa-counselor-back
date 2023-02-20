package dosa.counselor.web.dto.auth;

import dosa.counselor.common.encryption.AES256;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginRequestDto {
    private String email;
    private String password;

    @Builder
    public LoginRequestDto(String email, String password){
        this.email = AES256.encrypt(email);
        this.password = password;
    }

}
