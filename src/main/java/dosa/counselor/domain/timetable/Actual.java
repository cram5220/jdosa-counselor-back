package dosa.counselor.domain.timetable;

import dosa.counselor.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity(name = "COUNSELOR_TIMETABLE_ACTUAL")
public class Actual extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column
    private Long counselorIdx;

    @Column
    private LocalDate date;

    @Column
    private Short startTime;

    @Column
    private Short endTime;

    @Column
    private Boolean enabled;

    public void setEnabled(Boolean enabled){
        this.enabled = enabled;
    }

    @Builder
    public Actual(Long counselorIdx, LocalDate date, Short startTime, Short endTime, Boolean enabled){
        this.counselorIdx = counselorIdx;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.enabled = enabled;
    }

}
