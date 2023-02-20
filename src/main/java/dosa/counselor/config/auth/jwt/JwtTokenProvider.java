package dosa.counselor.config.auth.jwt;

import dosa.counselor.common.encryption.AES256;
import dosa.counselor.common.exception.AccessDeniedException;
import dosa.counselor.common.exception.BadCredentialsException;
import dosa.counselor.common.exception.ErrorCode;
import dosa.counselor.domain.token.RefreshToken;
import dosa.counselor.domain.user.Role;
import dosa.counselor.domain.user.User;
import dosa.counselor.service.token.TokenService;
import dosa.counselor.service.user.UserService;
import dosa.counselor.web.dto.auth.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import io.jsonwebtoken.*;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.Enumeration;

// 토큰을 생성하고 검증하는 클래스입니다.
// 해당 컴포넌트는 필터클래스에서 사전 검증을 거칩니다.
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    @Value("${jwt.secretKey}")
    private String secretKey;

    //private final Logger logger = (Logger) LoggerFactory.getLogger(LogAspect.class);

    // 토큰 유효시간 설정
    private long accessTokenValidTime = 30 * 60 * 1000L; // 30분
    private long refreshTokenValidTime = 3 * 60 * 60 * 1000L; // 3시간

    private final UserService userService;

    private final TokenService tokenService;

    // 객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // JWT 토큰 생성
    public String createToken(Long userPk, Role role) {
        Claims claims = Jwts.claims().setSubject(AES256.encrypt(userPk.toString())); // JWT payload 에 저장되는 정보단위, 보통 여기서 user를 식별하는 값을 넣는다.
        claims.put("role", role); // 정보는 key / value 쌍으로 저장된다.
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + accessTokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();
    }


    // jwt refresh 토큰 생성
    public String createRefreshToken(Long userPk) {
        //Claims claims = Jwts.claims().setSubject(userPk); // JWT payload 에 저장되는 정보단위, 보통 여기서 user를 식별하는 값을 넣는다.
        Date now = new Date();
        String refreshToken = Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        tokenService.saveOrUpdate(userPk,refreshToken);

        return refreshToken;
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) throws Exception {
        User user = userService.findByUserPk(Long.parseLong(this.getUserPk(token)));

        return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return AES256.decrypt(Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject());
    }

    public Role getUserRole(String token) {
        String roleName = (String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("role");
        return Role.valueOf(roleName);
    }

    // Request의 Header에서 token 값을 가져옵니다.
    public String resolveAccessToken(HttpServletRequest request) {
        //return request.getParameter("ACCESS_TOKEN");
        return request.getHeader("ACCESS_TOKEN");
    }

    public String resolveAccessToken(NativeWebRequest request) {
        //return request.getParameter("ACCESS_TOKEN");
        return request.getHeader("ACCESS_TOKEN");
    }
    public String resolveRefreshToken(HttpServletRequest request) {
        //return request.getParameter("REFRESH_TOKEN");
        return request.getHeader("REFRESH_TOKEN");
    }

    // 토큰의 유효성 + 만료일자 확인
    public String validateAccessToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            System.out.println("token expiration : " + claims.getBody().getExpiration().toString());
            if(!claims.getBody().getExpiration().before(new Date())){
                return "TRUE";
            }else{
                return "FALSE";
            }
        } catch (ExpiredJwtException e){
            return "FALSE";
        }catch (Exception e) {
            //throw new CustomException("invalid token", ErrorCode.TOKEN_INVALID);
            return "CANNOT READ";
        }
    }

    public RefreshToken getSavedRefreshToken(String refreshToken){
        RefreshToken savedToken = tokenService.getRefreshToken(refreshToken);
        return savedToken;
    }

    public boolean validateRefreshToken(String refreshToken, RefreshToken savedToken){

        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(refreshToken);
            //String userPk = this.getUserPk(accessToken);
            //Long userIdx = Long.parseLong(userPk);

            return savedToken.getRefreshToken().equals(refreshToken) && !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public Long getUserIdxFromAccessToken(HttpServletRequest request){
        String token = resolveAccessToken(request);
        if(token.equals("")||token==null){
            throw new AccessDeniedException("no tokens", ErrorCode.ACCESS_DENIED);
        }else if(validateAccessToken(token).equals("TRUE")){
            return Long.parseLong(getUserPk(token));
        }else{
            throw new AccessDeniedException("token expired", ErrorCode.ACCESS_DENIED);
        }
    }

    // 토큰이 있는지 체크
    public Boolean containsToken(HttpServletRequest request){
        Boolean hasToken = Boolean.FALSE;
        Enumeration<String> headers = request.getHeaderNames();
        String headerName;
        while (headers.hasMoreElements()){
            headerName = headers.nextElement();
            if(headerName.equals("access_token") || headerName.equals("ACCESS_TOKEN")){
                if(validateAccessToken(request.getHeader("ACCESS_TOKEN")).equals("TRUE")){
                    hasToken = Boolean.TRUE;
                }
                break;
            }
        }

        return hasToken;
    }


    // 리프레시 토큰 유효성 검사
    public TokenResponseDto refreshTokens(String refreshToken){
        RefreshToken savedToken = getSavedRefreshToken(refreshToken);
        if(savedToken!=null){
            if(validateRefreshToken(refreshToken,savedToken)){
                Long memberIdx = savedToken.getTokenID().getMemberIdx();
                return new TokenResponseDto(createToken(memberIdx, Role.COUNSELOR)
                        , createRefreshToken(memberIdx));
            }
        }

        throw new BadCredentialsException("토큰이 유효하지 않습니다");
    }


}