package dosa.counselor.config.auth;

import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Iterator;

@Component
public class CustomAuthorizationRequestResolver implements OAuth2AuthorizationRequestResolver {
    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();

        Iterator<String> headers = headerNames.asIterator();
        String header;
        while (headers.hasNext()){
            header = headers.next();
            System.out.println(header+" ==> "+request.getHeader(header));
        }

        return null;
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId) {
        return null;
    }
}
