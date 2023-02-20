package dosa.counselor.service.token;

import dosa.counselor.domain.token.RefreshToken;
import dosa.counselor.domain.token.RefreshTokenRepository;
import dosa.counselor.domain.token.TokenID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class TokenService {
    private final RefreshTokenRepository tokenRepository;

    @Transactional
    public RefreshToken findByMemberId(Long memberIdx){
        //return tokenRepository.findByMemberId(memberIdx).orElseThrow(()->new IllegalArgumentException("토큰이 없습니다"));
        return tokenRepository.findByMemberId(memberIdx).orElse(null);
    }


    @Transactional
    public RefreshToken getRefreshToken(String refreshToken){
        //return tokenRepository.findByMemberId(memberIdx).orElseThrow(()->new IllegalArgumentException("토큰이 없습니다"));
        return tokenRepository.getRefreshToken(refreshToken, LocalDateTime.now()).orElse(null);
    }

    @Transactional
    public void saveOrUpdate(Long memberIdx, String refreshToken){
        RefreshToken token = tokenRepository.findByMemberId(memberIdx).orElse(null);
        if(token!=null){
            token.update(refreshToken);
        }else{
            tokenRepository.save(new RefreshToken(memberIdx,refreshToken));
        }
    }

    @Transactional
    public void delete(Long memberIdx){
        tokenRepository.deleteById(new TokenID(memberIdx));
    }

    @Transactional
    public void removeOldTokens(Integer dayAfter) {
        tokenRepository.deleteAllByCreatedDateBefore(dayAfter, LocalDateTime.now());
    }

}
