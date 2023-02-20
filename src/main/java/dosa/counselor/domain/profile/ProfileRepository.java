package dosa.counselor.domain.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    @Query(nativeQuery = true, value = "SELECT [IDX] FROM [COUNSELOR_PROFILE] WHERE [COUNSELOR_IDX] IN (:counselorIdx)")
    List<Long> findIdxListByCounselorIdx(Long counselorIdx);

    @Query(nativeQuery = true, value = "SELECT TOP 1 [IDX] FROM [COUNSELOR_PROFILE] WHERE [COUNSELOR_IDX] IN (:counselorIdx)")
    Optional<Long> findIdxByCounselorIdx(Long counselorIdx);
}
