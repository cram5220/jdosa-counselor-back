package dosa.counselor.domain.authcode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AuthcodeRepository extends JpaRepository<Authcode, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM [AUTHCODE] WHERE [MEMBER_IDX]=:memberIdx AND [TYPE]=:type AND [CODE]=:code AND [EXPIRED_DATETIME] >= :now")
    Optional<Authcode> findByCodeAndExpired(Long memberIdx, Short type, String code, LocalDateTime now);
}
