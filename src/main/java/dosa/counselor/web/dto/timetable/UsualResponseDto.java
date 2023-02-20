package dosa.counselor.web.dto.timetable;

import dosa.counselor.domain.timetable.Usual;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UsualResponseDto {
    Short day;
    Short startTime;
    Short endTime;
    Boolean enabled;

    @Builder
    public UsualResponseDto(Usual entity){
        this.day = entity.getDay();
        this.startTime = entity.getStartTime();
        this.endTime = entity.getEndTime();
        this.enabled = entity.getEnabled();
    }

}
