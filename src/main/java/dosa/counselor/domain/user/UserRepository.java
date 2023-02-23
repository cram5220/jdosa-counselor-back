package dosa.counselor.domain.user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Transactional
    @Modifying
    @Query("update COUNSELOR c set c.pw = ?1 where c.id= ?2")
    int updatePwById(String pw, String email);
    Optional<User> findById(String email);

    Optional<User> findByIdx(Long idx);

    Optional<User> findTop1ByIdAndStatusIn(String id, Short[] status);

}