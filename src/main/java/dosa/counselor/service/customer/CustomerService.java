package dosa.counselor.service.customer;


import dosa.counselor.domain.customer.Customer;
import dosa.counselor.domain.customer.CustomerRepository;
import dosa.counselor.web.dto.customer.CustomerResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerResponseDto findByIdx(Long userIdx) {
        return new CustomerResponseDto(customerRepository.findByIdx(userIdx).orElseThrow(()->new RuntimeException("존재하지 않는 고객입니다.")));
    }
}
