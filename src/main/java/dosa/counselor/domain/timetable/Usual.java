package dosa.counselor.domain.timetable;

import dosa.counselor.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "COUNSELOR_TIMETABLE_USUAL")
public class Usual extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column
    private Long counselorIdx;

    @Column
    private Short day;

    @Column
    private Short startTime;

    @Column
    private Short endTime;

    @Column
    private Boolean enabled;


}
