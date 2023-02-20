package dosa.counselor.domain.timetable;

import dosa.counselor.web.dto.timetable.ActualDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ActualRepository extends JpaRepository<Actual, Long> {
    long countByCounselorIdxAndDate(Long counselorIdx, LocalDate date);

    List<Actual> findAllByCounselorIdxAndDate(Long counselorIdx, LocalDate date);

    Optional<Actual> findOneByCounselorIdxAndDateAndStartTime(Long counselorIdx, LocalDate date, Short startTime);

    @Modifying
    @Query(nativeQuery = true, value = "EXEC [dbo].[USUAL_TO_ACTUAL]\n" +
            "@counselorIdx = :counselorIdx")
    void updateActualFromUsual(Long counselorIdx);

    @Query(nativeQuery = true, value = "SELECT [DATE] FROM [COUNSELOR_TIMETABLE_ACTUAL] " +
            "WHERE [COUNSELOR_IDX]=:counselorIdx " +
            "AND [DATE]>=:startDate AND [DATE]<DATEADD(MONTH,1,:startDate) " +
            "AND [ENABLED]=1 GROUP BY [DATE] HAVING COUNT([ENABLED])>0")
    List<ActualDate> findAllWorkingDay(Long counselorIdx, LocalDate startDate);

}
