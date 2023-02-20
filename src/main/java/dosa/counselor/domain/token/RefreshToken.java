package dosa.counselor.domain.token;


import dosa.counselor.domain.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@NoArgsConstructor
@Getter
@Entity
public class RefreshToken extends BaseTimeEntity {


    @EmbeddedId
    private TokenID tokenID;

    @Column(length = 500)
    private String refreshToken;


    public RefreshToken(Long memberIdx, String refreshToken){
        this.refreshToken = refreshToken;
        this.tokenID = new TokenID(memberIdx);
    }

    public void update(String refreshToken){
        this.refreshToken = refreshToken;
    }


}
