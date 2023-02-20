package dosa.counselor.service.commonCode;

import dosa.counselor.domain.commonCode.CommonCode;
import dosa.counselor.domain.commonCode.CommonCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommonCodeService {

    private final CommonCodeRepository commonCodeRepository ;




    @Transactional(readOnly = true)
    public List<CommonCode> findAllByGroupCode(String groupCode1){
        return commonCodeRepository.findAllByGroupCode1(groupCode1);
    }


    @Transactional(readOnly = true)
    public List<CommonCode> findAllByGroupCode(String groupCode1, String groupCode2){
        return commonCodeRepository.findAllByGroupCode1AndGroupCode2(groupCode1,groupCode2);
    }

    @Transactional(readOnly = true)
    public List<CommonCode> findAllByGroupCode(String groupCode1, String groupCode2, String groupCode3){
        return commonCodeRepository.findAllByGroupCode1AndGroupCode2AndGroupCode3(groupCode1,groupCode2,groupCode3);
    }

    @Transactional(readOnly = true)
    public CommonCode findOneByValue(String groupCode1, Long value){
        return commonCodeRepository.findOneByGroupCode1AndValue(groupCode1, value);
    }

    @Transactional(readOnly = true)
    public CommonCode findOneByValue(String groupCode1, String groupCode2, Long value){
        return commonCodeRepository.findOneByGroupCode1AndGroupCode2AndValue(groupCode1,groupCode2, value);
    }

    @Transactional(readOnly = true)
    public CommonCode findOneByValue(String groupCode1, String groupCode2, String groupCode3, Long value){
        return commonCodeRepository.findOneByGroupCode1AndGroupCode2AndGroupCode3AndValue(groupCode1,groupCode2,groupCode3, value);
    }

    @Transactional(readOnly = true)
    public CommonCode findOneByCode(String groupCode1, String code){
        return commonCodeRepository.findOneByGroupCode1AndCode(groupCode1, code);
    }

    @Transactional(readOnly = true)
    public CommonCode findOneByCode(String groupCode1, String groupCode2, String code){
        return commonCodeRepository.findOneByGroupCode1AndGroupCode2AndCode(groupCode1,groupCode2, code);
    }

    @Transactional(readOnly = true)
    public CommonCode findOneByCode(String groupCode1, String groupCode2, String groupCode3, String code){
        return commonCodeRepository.findOneByGroupCode1AndGroupCode2AndGroupCode3AndCode(groupCode1,groupCode2,groupCode3, code);
    }



    @Transactional(readOnly = true)
    public CommonCode findOneByName(String groupCode1, String name){
        return commonCodeRepository.findOneByGroupCode1AndName(groupCode1, name);
    }

    @Transactional(readOnly = true)
    public CommonCode findOneByName(String groupCode1, String groupCode2, String name){
        return commonCodeRepository.findOneByGroupCode1AndGroupCode2AndName(groupCode1,groupCode2, name);
    }

    @Transactional(readOnly = true)
    public CommonCode findOneByName(String groupCode1, String groupCode2, String groupCode3, String name){
        return commonCodeRepository.findOneByGroupCode1AndGroupCode2AndGroupCode3AndName(groupCode1,groupCode2,groupCode3, name);
    }
}
