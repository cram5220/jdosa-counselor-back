package dosa.counselor.config.auth;


import dosa.counselor.config.auth.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@RequiredArgsConstructor
@EnableWebSecurity //스프링 시큐어리티 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .httpBasic().disable()

                .authorizeRequests()
                    .antMatchers("/counselor/login","/checkHealth","/**","/**/**").permitAll()
                    .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                    .anyRequest().authenticated()
                .and()
                .cors()
                .and()
                .logout()
                    .logoutSuccessUrl("/")
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginPage("http://localhost:3000")
                ;




    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();


        configuration.addAllowedOriginPattern("http://localhost:3000");

        configuration.addAllowedMethod("GET");
        configuration.addAllowedMethod("PUT");
        configuration.addAllowedMethod("POST");
        configuration.addAllowedMethod("DELETE");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}


// authorizeRequests : URL별 권한 관리를 설정하는 옵션의 시작점. 이게 선언되어야 antMatchers 옵션 사용 가능
// antMatchers : 권한 관리 지정 옵션. URL, HTTP 메소드별 관리 가능
// anyRequest 설정된 값들 이외 나머지 URL

//oauth2Login() oauth2 로그인 설정 진입점
// userInfoEndpoint : 로그인 성공 이후 사용자 정보 가져올 때 설정 담당
// userService 소셜 로그인 성공 시 후속조치를 진행할 UserService 인터페이스의 구현체를 등록
// 소셜 서비스에서 사용자 정보를 가져온 후 추가로 진행하고자 하는 작업 명시