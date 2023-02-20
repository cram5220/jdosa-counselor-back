package dosa.counselor.config.auth;


import dosa.counselor.common.exception.AccessDeniedException;
import dosa.counselor.common.exception.ErrorCode;
import dosa.counselor.config.auth.dto.AuthedUser;
import dosa.counselor.config.auth.jwt.JwtTokenProvider;
import dosa.counselor.domain.user.User;
import dosa.counselor.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    // 컨트롤러 메서드의 특정 파라미터를 지원하는지 판단
    //여기서는 @LoginUser 가 붙어있고 파라미터 클래스타임이 authedUser.class 면 true 반환
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        boolean isUserClass = AuthedUser.class.equals(parameter.getParameterType());
        return isLoginUserAnnotation && isUserClass;
    }

    // 파라미터에 전달할 객체 생성 - 수정
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityContextHolder.getContext().setAuthentication(null);

        if(authentication==null){ // 토큰 있는데 유효하지 않음
            String token = jwtTokenProvider.resolveAccessToken(webRequest);
            if(jwtTokenProvider.validateAccessToken(token).equals("FALSE")){ //만료임
                throw new AccessDeniedException("token expired", ErrorCode.ACCESS_DENIED);
            }else{ //양식이 안 맞음
                throw new AccessDeniedException("invalid token", ErrorCode.ACCESS_DENIED);
            }
        }else if(authentication.getPrincipal().equals("anonymousUser")){ //토큰 없는 비인증 유저
            throw new AccessDeniedException("no tokens", ErrorCode.ACCESS_DENIED);
        }else if(authentication.getPrincipal().getClass().equals(User.class)){
            User user = (User) authentication.getPrincipal();
            return new AuthedUser(user);
        }else if(authentication.getPrincipal().getClass().equals(DefaultOAuth2User.class)){
            DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
            Map<String, Object> attributes = oAuth2User.getAttributes();
            String email = attributes.get("email").toString();
            User user = null;
            try {
                user = userService.findByEmail(email);
            } catch (Exception e) {
                throw new IllegalArgumentException("no user has the email");
            }
            return new AuthedUser(user);
        }else {
            throw new AccessDeniedException("invalid token", ErrorCode.ACCESS_DENIED);
        }

    }
}
