package dosa.counselor.domain.user;

import dosa.counselor.common.encryption.AES256;
import dosa.counselor.common.encryption.BCrypt;
import dosa.counselor.domain.BaseTimeEntity;
import lombok.Builder;
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
@Entity(name = "COUNSELOR")
public class User extends BaseTimeEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(length = 200, nullable = false)
    private String email;

    @Column(nullable = false)
    private String pw;

    @Column(length = 50)
    private String name;

    @Column
    private Boolean luckychart;

    @Column
    private Boolean tarot;

    @Column(length = 50)
    private String cellphone;

    @Column(length = 20)
    private String zipcode;

    @Column(length = 500)
    private String address;

    @Column(length = 500)
    private String addressDetail;

    @Column
    private Boolean individualBusiness;

    @Column(length = 500)
    private String businessLicense;

    @Column
    private Short bank;

    @Column(length = 200)
    private String bankAccount;

    @Column(length = 200)
    private String kakaoId;

    @Column(length = 200)
    private LocalDateTime birthDate;

    @Column
    private Short gender;

    @Column
    private Float fixedCommission;

    @Column
    private Short status;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(Role.COUNSELOR.getKey()));
    }

    @Override
    public String getPassword() {
        return this.pw;
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
