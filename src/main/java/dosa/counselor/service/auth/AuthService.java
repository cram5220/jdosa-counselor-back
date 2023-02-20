package dosa.counselor.service.auth;

import dosa.counselor.common.CommonUtil;
import dosa.counselor.domain.authcode.Authcode;
import dosa.counselor.domain.authcode.AuthcodeRepository;
import dosa.counselor.service.aligo.AligoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthcodeRepository authcodeRepository;
    private final AligoService aligoService;

    private Integer smsAuthcodeLen = 5;

    //휴대폰 인증코드 전송
    @Transactional
    public Boolean sendAuthcode(Long userIdx, String number, Short authType){

        Boolean sendResult = false;

        if(CommonUtil.validCellphoneNumber(number)){ //휴대전화 번호가 검증됐으면
            //인증코드 생성
            String authcode = CommonUtil.ganerateRandomAuthcode(smsAuthcodeLen);
            //인증코드 저장 - insert 아니고 merge 로 변경
            authcodeRepository.save(new Authcode(userIdx, authType, authcode, 15L)); //type 1 sms, 2 email, 3 dirEnter
            //인증코드 발송 (성공 여부 리턴)
            sendResult = aligoService.sendAligoSms(number, "<정도사>\n\n 상담 ["+authcode+"] 입니다");
        }

        return sendResult;
    }

    //인증번호 검증
    @Transactional
    public Boolean validateAuthcode(Long memberIdx, Short authType, String authcode) {
        Boolean isAuthed = false;

        Authcode entity = authcodeRepository.findByCodeAndExpired(memberIdx, authType, authcode, LocalDateTime.now()).orElse(null);
        if(entity != null){
            isAuthed = true;
            authcodeRepository.delete(entity);
        }

        return isAuthed;
    }





}
