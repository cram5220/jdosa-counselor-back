package dosa.counselor.common.logger;

import dosa.counselor.config.auth.dto.AuthedUser;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class LogAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* dosa.counselor.web..*.*(..))")
    private void controllers() { }

    @Around("controllers()")
    public Object calcPerformanceAdvice(ProceedingJoinPoint pjp) throws Throwable {

        if(!pjp.getSignature().getName().contains("checkHealth")){
            logger.info("===== Contoller Start : "+pjp.getSignature().getName());
            logger.info("===== args : "+ Arrays.toString(pjp.getArgs()));

            List<Object> args = Arrays.asList(pjp.getArgs());
            for(Object arg: args){
                if(arg != null){
                    if(arg.getClass().equals(AuthedUser.class)){
                        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
                        String currentIp = req.getHeader("X-FORWARDED-FOR");
                        if (currentIp == null){
                            currentIp = req.getRemoteAddr();
                        }
                        logger.info("authedUser : "+((AuthedUser) arg).getIdx());
                    }
                }
            }
        }





        // 비즈니스 로직 (메인 로직)
        Object result = pjp.proceed();
        if(!pjp.getSignature().getName().contains("checkHealth")){
            logger.info("===== Contoller End : "+pjp.getSignature().getName());
        }


        return result;
    }



    //토큰 프로바이더 로그
    @Pointcut("execution(* dosa.counselor.config.auth.jwt.JwtTokenProvider.*(..))")
    private void tokenProvider() { }

    @Around("tokenProvider()")
    public Object tokenProviderAround(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("===== JWT function Start : "+pjp.getSignature().getName());
        logger.info("===== args : "+ Arrays.toString(pjp.getArgs()));

        // 비즈니스 로직 (메인 로직)
        Object result = pjp.proceed();
        logger.info("===== function End : "+pjp.getSignature().getName());

        return result;
    }


}
