package dosa.counselor.config.encoding;

import org.springframework.web.filter.CharacterEncodingFilter;

public class EucKrEncodingFilter extends CharacterEncodingFilter {
    public EucKrEncodingFilter() {
        setEncoding("EUC-KR");
        setForceEncoding(true);
    }
}
