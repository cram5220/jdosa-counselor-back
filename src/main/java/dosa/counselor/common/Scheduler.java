package dosa.counselor.common;

import dosa.counselor.common.encryption.AES256;
import dosa.counselor.domain.authcode.Authcode;
import dosa.counselor.domain.authcode.AuthcodeRepository;
import dosa.counselor.domain.commonCode.CommonCode;
import dosa.counselor.domain.counseling.CounselingView;
import dosa.counselor.domain.timetable.Usual;
import dosa.counselor.service.aligo.AligoService;
import dosa.counselor.service.auth.AuthService;
import dosa.counselor.service.commonCode.CommonCodeService;
import dosa.counselor.service.counseling.CounselingService;
import dosa.counselor.service.timetable.ActualTimetableService;
import dosa.counselor.service.timetable.UsualTimetableService;
import dosa.counselor.service.token.TokenService;
import dosa.counselor.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final TokenService tokenService;
    private final CounselingService counselingService;
    private final AuthService authService;
    private final AuthcodeRepository authcodeRepository;
    private final UserService userService;

    private final AligoService aligoService;

    private final CommonCodeService commonCodeService;

    @Value("${frontend-url}")
    private String frontUrl;

    // 리프레시 토큰 삭제
    @Scheduled(cron = "0 0 4 * * *")
    public void removeOldTokens(){
        tokenService.removeOldTokens(1);
    }


    // 매 5분마다 5분 뒤 시작되는 상담 알림 문자 발송 (15분 주기 아닌 이유 = 추후 상품 다양화)
    @Scheduled(cron = "0 0/5 * * * *")
    public void sendDirectEnterSms(){

        // 5분뒤 시작되는 상담 모두 불러옴
        List<CounselingView> list = counselingService.findAllByStartDtAndStatus(LocalDateTime.now().plusMinutes(5),(short)1);

        String code;
        String cellNumber;
        String msg=commonCodeService.findOneByCode("AUTHCODE","TYPE","DIRENTER").getText().replace("${frontUrl}",frontUrl);
        // 상담별로 인증코드 생성하여 DB 저장
        for(CounselingView counsel : list){
            //코드 생성
            code = counsel.getCounselorIdx().toString();
            code += "$";
            code += counsel.getIdx();
            code = URLEncoder.encode(AES256.encrypt(code));

            // DB 저장
            authcodeRepository.save(Authcode.builder()
                            .memberIdx(counsel.getCounselorIdx())
                            .type((short)3)
                            .code(code)
                            .expAfterMin(15L)
                    .build());

            cellNumber = userService.findByUserIdx(counsel.getCounselorIdx()).getCellphone();

            aligoService.sendAligoSms(cellNumber, msg+code);
        }

    }


    // 어드민 수정 전까진 주석처리
    
    // 매일 자정 30일 뒤의 usual -> actual
//    @Scheduled(cron = "0 0 0 * * *")
//    public void registActualTimetable() {
//        List<Usual> usualList = usualService.retrieveByDay(DataConverter.dateToWeekday(LocalDate.now().plusDays(30)));
//        actualService.registByUsual(usualList);
//    }


}
