package dosa.counselor.web.dto.counseling;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CancelRequestDto {
    private Long counselingIdx;
    private String cancelReason;

    @Builder
    public CancelRequestDto(Long counselingIdx, String cancelReason){
        this.counselingIdx = counselingIdx;
        this.cancelReason = cancelReason;
    }
}
