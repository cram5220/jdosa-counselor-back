package dosa.counselor.web;

import dosa.counselor.config.auth.LoginUser;
import dosa.counselor.config.auth.dto.AuthedUser;
import dosa.counselor.service.timetable.ActualTimetableService;
import dosa.counselor.service.timetable.UsualTimetableService;
import dosa.counselor.web.dto.timetable.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/*
 * 일정 정보 컨트롤러 (업무 시간, 휴무 등)
 * */
@RequiredArgsConstructor@RestController
public class TimetableController {

    private final UsualTimetableService usualService;
    private final ActualTimetableService actualService;

    /*
    * Usual : 상담시간 수정
    * */
    @PutMapping("/schedule/usual")
    public void modifyUsualSchedule(@LoginUser AuthedUser user,
                                    @RequestBody List<UsualRequestDto> usualList
                                    ) {
        usualService.modifyTimetable(user.getIdx(), usualList);
    }


    /*
     * Usual : 상담시간 조회
     * */
    @GetMapping("/schedule/usual")
    public List<UsualResponseDto> retrieveUsualSchedule(@LoginUser AuthedUser user) {
        return usualService.retrieveTaimeTalbe(user.getIdx());
    }

    /*
     * Actual : 상담시간 조회
     * */
    @GetMapping("/schedule/actual")
    public List<ActualResponseDto> retrieveActualSchedule(@LoginUser AuthedUser user, @RequestParam("date") String date) {
        return actualService.retrieveTaimeTalbe(user.getIdx(), date);
    }

    /*
     * Actual : 상담시간 수정
     * */
    @PutMapping("/schedule/actual")
    public void modifyActualSchedule(@LoginUser AuthedUser user,
                                    @RequestBody List<ActualRequestDto> actualList) {
        actualService.modifyTimetable(user.getIdx(), actualList);
    }

    /*
     * Actual : 휴무일 조회
     * */
    @GetMapping("/schedule/workingday")
    public List<ActualDate> retrieveWorkingDay(@LoginUser AuthedUser user,
                                               @RequestParam("year") Integer year,
                                               @RequestParam("month") Integer month){
        return actualService.retrieveWorkingDay(user.getIdx(), year, month);
    }


}
