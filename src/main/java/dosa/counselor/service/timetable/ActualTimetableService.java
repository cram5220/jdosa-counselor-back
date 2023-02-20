package dosa.counselor.service.timetable;

import dosa.counselor.common.DataConverter;
import dosa.counselor.domain.timetable.Actual;
import dosa.counselor.domain.timetable.ActualRepository;
import dosa.counselor.domain.timetable.Usual;
import dosa.counselor.domain.timetable.UsualRepository;
import dosa.counselor.web.dto.timetable.ActualDate;
import dosa.counselor.web.dto.timetable.ActualRequestDto;
import dosa.counselor.web.dto.timetable.ActualResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ActualTimetableService {
    private final ActualRepository actualRepository;
    private final UsualRepository usualRepository;


    // 특정 날짜의 오픈 시간 조회
    public List<ActualResponseDto> retrieveTaimeTalbe(Long counselorIdx, String date) {
        return actualRepository.findAllByCounselorIdxAndDate(counselorIdx, DataConverter.stringToDate(date)).stream().map(ActualResponseDto::new).collect(Collectors.toList());
    }

    // 특정 날짜의 상담 시간 수정
    @Transactional
    public void modifyTimetable(Long counselorIdx, List<ActualRequestDto> actualList) {
        for(ActualRequestDto dto: actualList){
            Actual entity = actualRepository.findOneByCounselorIdxAndDateAndStartTime(counselorIdx, DataConverter.stringToDate(dto.getDate()), dto.getStartTime()).orElseThrow(()->new RuntimeException("존재하지 않는 값입니다"));
            entity.setEnabled(dto.getEnabled());
            actualRepository.save(entity);
        }
    }

    // 스케쥴러에서 사용 : 모든 상담사의 30일 뒤 날짜 오픈
    public void registByUsual(List<Usual> usualList) {
        LocalDate thirtyAfter = LocalDate.now().plusDays(30);
        for(Usual usual: usualList){
            actualRepository.save(Actual.builder()
                            .counselorIdx(usual.getCounselorIdx())
                            .date(thirtyAfter)
                            .startTime(usual.getStartTime())
                            .endTime(usual.getEndTime())
                            .enabled(usual.getEnabled())
                    .build());
        }
    }

    @Transactional
    // usual 변경 시 오늘 이후 30일 뒤까지 actual merge
    public void modifyActualTimetableFromNow(Long counselorIdx) {

        long count = 0;
        // 해당 상담사의 없는 시간 채워주기
        for(int i=0; i<=30; i++){

            count = actualRepository.countByCounselorIdxAndDate(counselorIdx, LocalDate.now().plusDays(i));
            if(count<24){
                for(Short j=0; j<24; j++){
                    actualRepository.save(Actual.builder()
                                    .counselorIdx(counselorIdx)
                                    .date(LocalDate.now().plusDays(i))
                                    .startTime(j)
                                    .endTime((short) (j+1))
                                    .enabled(Boolean.FALSE)
                            .build());
                }
            }

//            for(Short j=0; j<24; j++){
//                actualRepository.insertEmptyActual(counselorIdx, LocalDate.now().plusDays(i), j);
//            }
        }

        // 해당 상담사의 actual 업데이트
        actualRepository.updateActualFromUsual(counselorIdx);
    }

    public List<ActualDate> retrieveWorkingDay(Long counselorIdx, Integer year, Integer month) {
        LocalDate startDate = LocalDate.of(year,month,1);
        return actualRepository.findAllWorkingDay(counselorIdx, startDate);
    }
}
