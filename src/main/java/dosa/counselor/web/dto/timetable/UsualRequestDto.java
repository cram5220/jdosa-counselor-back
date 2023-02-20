package dosa.counselor.web.dto.timetable;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UsualRequestDto {
    Short day;
    Short startTime;
    Boolean enabled;


    @Builder
    public UsualRequestDto(Short day, Short startTime, Boolean enabled){
        this.day = day;
        this.startTime = startTime;
        this.enabled = enabled;
    }

}
