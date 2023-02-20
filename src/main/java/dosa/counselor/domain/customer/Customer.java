package dosa.counselor.domain.customer;

import dosa.counselor.domain.BaseTimeEntity;
import dosa.counselor.domain.user.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Getter
@NoArgsConstructor
@Entity(name = "USER")
public class Customer extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(length = 50, nullable = false)
    private String name;
//
//    @Column(length = 100, nullable = false)
//    private String email;
//
//    @Column(length = 20, nullable = false)
//    private String role;

    @Column
    private Boolean birthCalendarType;

    @Column(length = 50)
    private String birthDate;

    @Column(length = 50)
    private String birthTime;

    @Column(length = 50)
    private String gender;
//
//    @Column(length = 50)
//    private String loginApi;
//
//    @Column
//    private Short status;
//
//    @Column(length = 200)
//    private String cellphone;
//
//    @Column
//    private Boolean smsRcvAgree;
//
//    @Column
//    private Boolean emailRcvAgree;


}
