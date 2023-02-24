package dosa.counselor.domain.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class TokenID implements Serializable {
    private Long memberIdx;
    private Short memberType;

    public TokenID(Long memberIdx) {
        this.memberIdx=memberIdx;
        this.memberType=2;
    }
}
