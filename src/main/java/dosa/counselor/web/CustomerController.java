package dosa.counselor.web;

import dosa.counselor.config.auth.LoginUser;
import dosa.counselor.config.auth.dto.AuthedUser;
import dosa.counselor.service.customer.CustomerService;
import dosa.counselor.web.dto.customer.CustomerResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CustomerController {

    private final CustomerService customerService;

    //회원 정보 조회
    @GetMapping("/customer/{idx}")
    public CustomerResponseDto retrieveCustomer(@LoginUser AuthedUser user,
                                                @PathVariable("idx") Long userIdx){
        return customerService.findByIdx(userIdx);
    }


}
