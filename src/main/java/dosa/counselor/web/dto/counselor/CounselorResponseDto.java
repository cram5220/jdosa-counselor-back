package dosa.counselor.web.dto.counselor;

import dosa.counselor.common.encryption.AES256;
import dosa.counselor.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import java.time.LocalDateTime;


@Getter
public class CounselorResponseDto {

    private String id;

    private String name;

    private Boolean luckychart;

    private Boolean tarot;

    private String cellphone;

    private Boolean individualBusiness;

    private String bank;

    private String bankAccount;

    private Short status;
    @Builder
    public CounselorResponseDto(User entity, String bank){
        this.id = AES256.decrypt(entity.getId());
        this.name = entity.getName();
        this.luckychart = entity.getLuckychart();
        this.tarot = entity.getTarot();
        this.cellphone = entity.getCellphone();
        this.individualBusiness = entity.getIndividualBusiness();
        this.bank = bank;
        this.bankAccount = AES256.decrypt(entity.getBankAccount());
        this.status = entity.getStatus();
    }


}
