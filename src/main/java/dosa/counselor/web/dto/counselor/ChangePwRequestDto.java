package dosa.counselor.web.dto.counselor;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChangePwRequestDto {
    private String current;
    private String newPw;

    @Builder
    public ChangePwRequestDto(String current, String newPw){
        this.current = current;
        this.newPw = newPw;
    }
}
