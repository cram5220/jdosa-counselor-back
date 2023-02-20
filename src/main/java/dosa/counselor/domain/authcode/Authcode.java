package dosa.counselor.domain.authcode;

import dosa.counselor.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity(name = "AUTHCODE")
public class Authcode extends BaseTimeEntity {

    @EmbeddedId
    private AuthcodeID authcodeID;

    @Column(length = 50)
    private String code;

    @Column
    private LocalDateTime expiredDatetime;

    @Builder
    public Authcode(Long memberIdx, Short type, String code, Long expAfterMin){
        this.authcodeID = new AuthcodeID(memberIdx,type);
        this.code = code;
        this.expiredDatetime = LocalDateTime.now().plusMinutes(expAfterMin);
    }
}
