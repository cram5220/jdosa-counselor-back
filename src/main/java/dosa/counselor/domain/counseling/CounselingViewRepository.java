package dosa.counselor.domain.counseling;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CounselingViewRepository extends JpaRepository<CounselingView, Long> {
    Page<CounselingView> findAllByCounselorIdxAndStatusIn(Long idx, Short[] status, Pageable pageable);

    Optional<CounselingView> findTop1ByCounselorIdxAndStatusInOrderByCounselingDatetime(Long idx, Short[] status);

    List<CounselingView> findAllByCounselorIdxAndCounselingDateAndStatusInOrderByStartTime(Long counselorIdx, LocalDate date, Short[] status);

    @Query(nativeQuery = true, value = "SELECT * FROM [COUNSELING_LIST] " +
            "WHERE [COUNSELING_DATETIME] >= DATEADD(MINUTE,-1,:time) AND [COUNSELING_DATETIME] <= DATEADD(MINUTE,1,:time) ")
    List<CounselingView> findAllByStartDt(LocalDateTime time);

    @Query(nativeQuery = true, value = "SELECT * FROM [COUNSELING_LIST] " +
            "WHERE [COUNSELING_DATETIME] >= DATEADD(MINUTE,-1,:time) AND [COUNSELING_DATETIME] <= DATEADD(MINUTE,1,:time) " +
            "AND [STATUS]=:status")
    List<CounselingView> findAllByStartDtAndStatus(LocalDateTime time, Short status);
}
