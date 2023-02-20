package dosa.counselor.domain.timetable;

import dosa.counselor.common.DataConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

import java.time.LocalDateTime;

public interface UsualRepository extends JpaRepository<Usual, Long> {
    long countByCounselorIdx(Long counselorIdx);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE [COUNSELOR_TIMETABLE_USUAL] SET [ENABLED]=:enabled WHERE [COUNSELOR_IDX]=:counselorIdx " +
            "AND [DAY]=:day AND [START_TIME]=:startTime")
    void updateEnabled(Boolean enabled, Long counselorIdx, Short day, Short startTime);


    @Modifying
    @Query(nativeQuery = true,value = "INSERT INTO [COUNSELOR_TIMETABLE_USUAL] ([COUNSELOR_IDX],[DAY],[START_TIME],[END_TIME],[ENABLED],[CREATED_DATE]) " +
            "VALUES (:counselorIdx, :day, :startTime, (:startTime+1), 0, GETDATE())")
    void insertDefault(Long counselorIdx, Short day, Short startTime);

    void deleteAllByCounselorIdx(Long counselorIdx);

    List<Usual> findAllByCounselorIdx(Long counselorIdx);

    @Query(nativeQuery = true, value = "SELECT * FROM [COUNSELOR_TIMETABLE_USUAL] " +
            "WHERE [DAY]=:dateToWeekday AND [COUNSELOR_IDX] NOT IN (SELECT [IDX] FROM [COUNSELOR] WHERE [STATUS]=3)")
    List<Usual> findAllByDayAndCounselorIdxNotIn(Short dateToWeekday);
}
