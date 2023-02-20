package dosa.counselor.web.dto.timetable;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ActualRequestDto {
    String date;
    Short startTime;
    Boolean enabled;


    @Builder
    public ActualRequestDto(String date, Short startTime, Boolean enabled){
        this.date = date;
        this.startTime = startTime;
        this.enabled = enabled;
    }

}
