package dosa.counselor.domain.commonCode;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommonCodeRepository extends JpaRepository<CommonCode, Long>  {

    List<CommonCode> findAllByGroupCode1(String groupCode1);

    List<CommonCode> findAllByGroupCode1AndGroupCode2(String groupCode1, String groupCode2);

    List<CommonCode> findAllByGroupCode1AndGroupCode2AndGroupCode3(String groupCode1, String groupCode2, String groupCode3);

    CommonCode findOneByGroupCode1AndValue(String groupCode1, Long value);

    CommonCode findOneByGroupCode1AndGroupCode2AndValue(String groupCode1, String groupCode2, Long value);

    CommonCode findOneByGroupCode1AndGroupCode2AndGroupCode3AndValue(String groupCode1, String groupCode2, String groupCode3, Long value);

    CommonCode findOneByGroupCode1AndCode(String groupCode1, String code);

    CommonCode findOneByGroupCode1AndGroupCode2AndCode(String groupCode1, String groupCode2, String code);

    CommonCode findOneByGroupCode1AndGroupCode2AndGroupCode3AndCode(String groupCode1, String groupCode2, String groupCode3, String code);

    CommonCode findOneByGroupCode1AndName(String groupCode1, String name);

    CommonCode findOneByGroupCode1AndGroupCode2AndName(String groupCode1, String groupCode2, String name);

    CommonCode findOneByGroupCode1AndGroupCode2AndGroupCode3AndName(String groupCode1, String groupCode2, String groupCode3, String name);

}
