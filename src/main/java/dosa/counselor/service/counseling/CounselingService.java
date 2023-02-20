package dosa.counselor.service.counseling;


import dosa.counselor.common.exception.BadCredentialsException;
import dosa.counselor.common.exception.ErrorCode;
import dosa.counselor.common.exception.IllegalArgumentException;
import dosa.counselor.domain.counseling.Counseling;
import dosa.counselor.domain.counseling.CounselingRepository;
import dosa.counselor.domain.counseling.CounselingView;
import dosa.counselor.domain.counseling.CounselingViewRepository;
import dosa.counselor.web.dto.counseling.CounselingResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CounselingService {
    private final CounselingViewRepository counselingViewRepository;
    private final CounselingRepository counselingRepository;


    /*
     * 목록 조회
     * statuses : ['ongoing','cancel'[, ...]]
     * */
    public Page<CounselingView> findAllByCounselorIdxAndStatusIn(Long idx, Short[] status, Pageable pageable){

        return counselingViewRepository.findAllByCounselorIdxAndStatusIn(idx, status, pageable);

    }

    /*
     * 단일 조회 : 인덱스
     * idx : 상담 고유 번호
     * */
    public CounselingResponseDto findById(Long idx) {
        return new CounselingResponseDto(counselingViewRepository.findById(idx).orElseThrow(()->new IllegalArgumentException("존재하지 않는 상담입니다")));
    }

    public CounselingResponseDto findTop1ByCounselorIdxAndStatusInOrderByCounselingDatetime(Long counselorIdx, Short[] status) {

        CounselingView entity = counselingViewRepository.findTop1ByCounselorIdxAndStatusInOrderByCounselingDatetime(counselorIdx,status)
                .orElse(null);

        if(entity!=null){
            return new CounselingResponseDto(entity);
        }else {
            return null;
        }
    }

    public List<CounselingResponseDto> findListByDate(Long userIdx, LocalDate date) {
        Short[] status = {1,2,3,4};
        return counselingViewRepository.findAllByCounselorIdxAndCounselingDateAndStatusInOrderByStartTime(userIdx, date,status).stream()
                .map(CounselingResponseDto::new).collect(Collectors.toList());
    }


    // 예약 취소
    public void cancelCounseling(Long counselorIdx, Long counselingIdx, String cancelReason) {
        CounselingView entity = counselingViewRepository.findById(counselingIdx).orElseThrow(()->new RuntimeException("존재하지 않는 상담건입니다"));
        if(entity.getCounselorIdx().equals(counselorIdx)){
            Counseling counseling = counselingRepository.findById(counselingIdx).orElseThrow(()->new RuntimeException("존재하지 않는 상담건입니다"));
            counseling.cancel(cancelReason);
            counselingRepository.save(counseling);
        }else{
            throw new BadCredentialsException(ErrorCode.ACCESS_DENIED);
        }
    }


    public List<CounselingView> findAllByStartDt(LocalDateTime time) {
        return counselingViewRepository.findAllByStartDt(time);
    }

    public List<CounselingView> findAllByStartDtAndStatus(LocalDateTime time, Short status) {
        return counselingViewRepository.findAllByStartDtAndStatus(time, status);
    }
}
