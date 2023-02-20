package dosa.counselor.web.dto.timetable;

import dosa.counselor.domain.timetable.Actual;
import dosa.counselor.domain.timetable.Usual;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ActualResponseDto {
    LocalDate date;
    Short startTime;
    Boolean enabled;

    @Builder
    public ActualResponseDto(Actual entity){
        this.date = entity.getDate();
        this.startTime = entity.getStartTime();
        this.enabled = entity.getEnabled();
    }

}
