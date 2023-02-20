package dosa.counselor.service.timetable;

import dosa.counselor.domain.timetable.Usual;
import dosa.counselor.domain.timetable.UsualRepository;
import dosa.counselor.domain.user.UserRepository;
import dosa.counselor.web.dto.timetable.UsualRequestDto;
import dosa.counselor.web.dto.timetable.UsualResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UsualTimetableService {
    private final UsualRepository usualRepository;
    private final ActualTimetableService actualService;

    @Transactional
    public void modifyTimetable(Long counselorIdx, List<UsualRequestDto> usualList) {

        //해당 상담사의 usual 이 존재하는지 먼저 확인
        if(!retrieveTimetable(counselorIdx)){ //존재하지 않으면 insert 먼저
            usualRepository.deleteAllByCounselorIdx(counselorIdx);
            registTimetable(counselorIdx);
        }
        for(UsualRequestDto dto : usualList) {
            usualRepository.updateEnabled(dto.getEnabled(), counselorIdx, dto.getDay(), dto.getStartTime());
        }
        
        // 오늘 이후 모든 actual 변경
        actualService.modifyActualTimetableFromNow(counselorIdx);
    }

    private Boolean retrieveTimetable(Long counselorIdx){
        Long count = usualRepository.countByCounselorIdx(counselorIdx);
        if(count==24*7){
            return Boolean.TRUE;
        }else {
            return Boolean.FALSE;
        }
    }

    private void registTimetable(Long counselorIdx){
        for(Short day=1; day<=7; day++){
            for(Short startTime=0; startTime<24; startTime++){
                usualRepository.insertDefault(counselorIdx, day, startTime);
            }
        }
    }

    public List<UsualResponseDto> retrieveTaimeTalbe(Long counselorIdx) {
        return usualRepository.findAllByCounselorIdx(counselorIdx).stream().map(UsualResponseDto::new).collect(Collectors.toList());
    }

    public List<Usual> retrieveByDay(Short dateToWeekday) {
        //return usualRepository.findAllByDay(dateToWeekday);
        return usualRepository.findAllByDayAndCounselorIdxNotIn(dateToWeekday);
    }
}
