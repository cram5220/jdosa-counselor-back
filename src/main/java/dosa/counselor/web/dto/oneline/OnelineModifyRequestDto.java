package dosa.counselor.web.dto.oneline;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OnelineModifyRequestDto {

    Long idx;
    String replyBody;

    @Builder
    public OnelineModifyRequestDto(Long idx, String replyBody){
        this.idx = idx;
        this.replyBody = replyBody;
    }

}
