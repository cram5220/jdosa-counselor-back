package dosa.counselor.config;


import dosa.counselor.config.encoding.EucKrEncodingFilter;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class PropertyConfig {


    @Bean
    FilterRegistrationBean<EucKrEncodingFilter> eucKrEncodingFilter() {
        FilterRegistrationBean<EucKrEncodingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new EucKrEncodingFilter());
        registrationBean.addUrlPatterns("/payment/kgmb_ok");
        registrationBean.addUrlPatterns("/payment/kgmb_noti");
        return registrationBean;
    }


}
