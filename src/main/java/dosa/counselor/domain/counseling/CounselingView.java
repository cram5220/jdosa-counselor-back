package dosa.counselor.domain.counseling;

import dosa.counselor.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
@Entity(name = "COUNSELING_LIST")
public class CounselingView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable=false)
    private Long userIdx;

    @Column(length = 200)
    private String cellphone;

    @Column(length=100)
    private String leavedMsg;

    @Column
    private LocalDate counselingDate;

    @Column
    private LocalTime startTime;

    @Column
    private LocalTime endTime;

    @Column
    private Long productIdx;

    @Column
    private Short counselingType;

    @Column
    private Short counselingTime;

    @Column
    private Long counselorIdx;

    @Column
    private Short profileType;

    @Column(length=12)
    private String nickname;

    @Column(length=50)
    private String picture;

    @Column
    private Long profileIdx;

    @Column
    private Short status;

    @Column
    private LocalDateTime counselingDatetime;

    @Column(length = 50)
    private String productName;

    @Column(nullable = false)
    private Long price;


    @Builder
    public CounselingView(Long userIdx, String cellphone, String leavedMsg,LocalDate counselingDate,
                          LocalTime startTime, LocalTime endTime,
                          Long productIdx,Short counselingType,Short counselingTime,Long counselorIdx,
                          Short profileType,String nickname,String picture, Long profileIdx,Short status,
                          LocalDateTime counselingDatetime, String productName, Long price){
        this.userIdx = userIdx;
        this.cellphone = cellphone;
        this.leavedMsg = leavedMsg;
        this.counselingDate = counselingDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.productIdx = productIdx;
        this.counselingType = counselingType;
        this.counselingTime = counselingTime;
        this.counselorIdx = counselorIdx;
        this.profileType = profileType;
        this.nickname = nickname;
        this.picture = picture;
        this.profileIdx = profileIdx;
        this.status = status;
        this.counselingDatetime = counselingDatetime;
        this.productName = productName;
        this.price = price;
    }


}
