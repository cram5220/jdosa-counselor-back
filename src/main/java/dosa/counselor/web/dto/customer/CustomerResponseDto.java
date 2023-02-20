package dosa.counselor.web.dto.customer;

import dosa.counselor.common.encryption.AES256;
import dosa.counselor.domain.customer.Customer;
import dosa.counselor.service.customer.CustomerService;
import lombok.Builder;
import lombok.Getter;


@Getter
public class CustomerResponseDto {

    private Long idx;
    private String name;
    private Boolean birthCalendarType;
    private String birthDate;
    private String birthTime;
    private String gender;

    @Builder
    public CustomerResponseDto(Customer entity){
        this.idx = entity.getIdx();
        this.name = AES256.decrypt(entity.getName());
        this.birthCalendarType = entity.getBirthCalendarType();
        this.birthDate = AES256.decrypt(entity.getBirthDate());
        this.birthTime = AES256.decrypt(entity.getBirthTime());
        this.gender = AES256.decrypt(entity.getGender());
    }

}
