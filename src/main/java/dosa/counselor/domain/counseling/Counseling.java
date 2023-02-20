package dosa.counselor.domain.counseling;

import dosa.counselor.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "USER_COUNSELING")
public class Counseling extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable=false)
    private Long userIdx;

    @Column
    private Long productIdx;

    @Column
    private LocalDateTime startDt;

    @Column
    private LocalDateTime endDt;

    @Column(length=100)
    private String leavedMsg;

    @Column
    private Short status;

    @Column
    private Short ageGroup;

    @Column
    private Short gender;

    @Column
    private LocalDateTime cancelRequestedDate;

    @Column
    private Short cancelRequestor;

    @Column(length=100)
    private String cancelReason;


    @Builder
    public Counseling(Long userIdx, Long productIdx, LocalDateTime startDt, LocalDateTime endDt,
                      String leavedMsg, Short status, Short ageGroup, Short gender){
        this.userIdx = userIdx;
        this.productIdx = productIdx;
        this.startDt = startDt;
        this.endDt = endDt;
        this.leavedMsg = leavedMsg;
        this.status = status;
        this.ageGroup = ageGroup;
        this.gender = gender;
    }


    public void cancel(String cancelReason){
        this.status=8;
        this.cancelReason = cancelReason;
        this.cancelRequestor = 2;
        this.cancelRequestedDate = LocalDateTime.now();
    }

}
