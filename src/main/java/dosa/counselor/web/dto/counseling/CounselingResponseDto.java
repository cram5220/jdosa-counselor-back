package dosa.counselor.web.dto.counseling;

import dosa.counselor.common.DataConverter;
import dosa.counselor.domain.counseling.CounselingView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
public class CounselingResponseDto {
    private Long idx;
    private Long counselorIdx;
    private Long userIdx;
    private String leavedMsg;
    private String counselingDate;
    private String startTime;
    private String endTime;
    private Long productIdx;
    private Short counselingType;
    private Short counselingTime;
    private Short profileType;
    private String nickname;
    private Short status;
    private String counselingDatetime;
    private String productName;
    private Long price;

    @Builder
    public CounselingResponseDto(CounselingView entity){
        this.idx = entity.getIdx();
        this.counselorIdx = entity.getCounselorIdx();
        this.userIdx = entity.getUserIdx();
        this.leavedMsg = entity.getLeavedMsg();
        this.counselingDate = DataConverter.dateToString(entity.getCounselingDate());
        this.startTime = DataConverter.timeToString(entity.getStartTime());
        this.endTime = DataConverter.timeToString(entity.getEndTime());
        this.productIdx = entity.getProductIdx();
        this.counselingType = entity.getCounselingType();
        this.counselingTime = entity.getCounselingTime();
        this.profileType = entity.getProfileType();
        this.nickname = entity.getNickname();
        this.status = entity.getStatus();
        this.counselingDatetime = DataConverter.datetimeToString(entity.getCounselingDatetime());
        this.productName = entity.getProductName();
        this.price = entity.getPrice();
    }


}
