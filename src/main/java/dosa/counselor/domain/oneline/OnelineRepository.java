package dosa.counselor.domain.oneline;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface OnelineRepository extends JpaRepository<Oneline,Long> {
    @Transactional
    @Modifying
    @Query("update ONELINE_COUNSELING_LIST o set o.profileIdx = ?1, o.replyBody = ?2, o.repliedDate = ?3 where o.idx= ?4")
    void updateProfileIdxAndReplyBodyAndRepliedDateByIdx(Long profileIdx, String replyBody, LocalDateTime repliedDate, Long idx);
    Page<Oneline> findAllByProfileIdxInOrProfileIdxIsNull(Long[] profileIdxList, Pageable pageable);



}
