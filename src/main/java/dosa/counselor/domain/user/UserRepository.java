package dosa.counselor.domain.user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Transactional
    @Modifying
    @Query("update COUNSELOR c set c.pw = ?1 where c.email= ?2")
    int updatePwByEmail(String pw, String email);
    Optional<User> findByEmail(String email); //소셜 로그인으로 반환되는 값 중 email 을 통해 기존 사용자인지 판단

    Optional<User> findByIdx(Long idx);

    Optional<User> findTop1ByEmailAndStatusIn(String email, Short[] status);

}