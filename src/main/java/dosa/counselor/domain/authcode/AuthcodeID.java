package dosa.counselor.domain.authcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class AuthcodeID implements Serializable {
    private Long memberIdx;
    private Short type;
}
