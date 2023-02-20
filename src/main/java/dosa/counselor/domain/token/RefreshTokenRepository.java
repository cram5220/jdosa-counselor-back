package dosa.counselor.domain.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,TokenID> {
    @Query(nativeQuery = true, value = "SELECT * FROM [REFRESH_TOKEN] WHERE [MEMBER_TYPE]=2 AND [MEMBER_IDX]=:memberIdx")
    Optional<RefreshToken> findByMemberId(Long memberIdx);

    @Query(nativeQuery = true, value = "SELECT * FROM [REFRESH_TOKEN] WHERE [REFRESH_TOKEN]=:refreshToken AND [MODIFIED_DATE]>=DATEADD(HOUR,-3,GETDATE())")
    Optional<RefreshToken> getRefreshToken(String refreshToken);

    @Query(nativeQuery = true, value = "SELECT * FROM [REFRESH_TOKEN] WHERE [REFRESH_TOKEN]=:refreshToken AND [MODIFIED_DATE]>=DATEADD(HOUR,-3,:now)")
    Optional<RefreshToken> getRefreshToken(String refreshToken, LocalDateTime now);

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM [REFRESH_TOKEN] WHERE [MODIFIED_DATE]<DATEADD(DAY,(-1 * :dayAfter),GETDATE())")
    void deleteAllByCreatedDateBefore(Integer dayAfter);

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM [REFRESH_TOKEN] WHERE [MODIFIED_DATE]<DATEADD(DAY,(-1 * :dayAfter),:now)")
    void deleteAllByCreatedDateBefore(Integer dayAfter, LocalDateTime now);

}
