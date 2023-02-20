package dosa.counselor.config.auth.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

//해당 클래스는 JwtTokenProvider가 검증을 끝낸 Jwt로부터 유저 정보를 조회해와서 UserPasswordAuthenticationFilter 로 전달합니다.
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException, AccessDeniedException {

        // 헤더에서 토큰 추출
        String accessToken = jwtTokenProvider.resolveAccessToken((HttpServletRequest) request);
        // 토큰 유효성 검증
        if (accessToken != null && jwtTokenProvider.validateAccessToken(accessToken).equals("TRUE")) {
            // 인증 가져오기
            Authentication authentication = null;
            try {
                authentication = jwtTokenProvider.getAuthentication(accessToken);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // SecurityContext 에 Authentication 객체를 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }else if(accessToken != null){ //토큰이 있는데 유효하지 않으면
            SecurityContextHolder.getContext().setAuthentication(null);
            //SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        }
        chain.doFilter(request, response);
    }

}