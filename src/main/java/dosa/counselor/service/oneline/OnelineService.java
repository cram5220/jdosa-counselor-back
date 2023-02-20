package dosa.counselor.service.oneline;


import dosa.counselor.domain.oneline.Oneline;
import dosa.counselor.domain.oneline.OnelineRepository;
import dosa.counselor.domain.profile.ProfileRepository;
import dosa.counselor.web.dto.oneline.OnelineModifyRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OnelineService {

    private final OnelineRepository onelineRepository;
    private final ProfileRepository profileRepository;

    public Page<Oneline> findAllReplyAvailable(Long counselorIdx, Pageable pageable) {
        List<Long> profileList = profileRepository.findIdxListByCounselorIdx(counselorIdx);

        return onelineRepository.findAllByProfileIdxInOrProfileIdxIsNull(profileList.toArray(new Long[0]), pageable);
    }

    public void registReplyBody(Long counselorIdx, OnelineModifyRequestDto dto) {
        Long profileIdx = profileRepository.findIdxByCounselorIdx(counselorIdx).orElseThrow(()->new RuntimeException("상담사 프로필이 존재하지 않습니다"));
        onelineRepository.updateProfileIdxAndReplyBodyAndRepliedDateByIdx(profileIdx, dto.getReplyBody(), LocalDateTime.now(),dto.getIdx());
    }
}
