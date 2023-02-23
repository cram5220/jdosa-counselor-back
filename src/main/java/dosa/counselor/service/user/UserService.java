package dosa.counselor.service.user;

import dosa.counselor.common.encryption.BCrypt;
import dosa.counselor.common.exception.BadCredentialsException;
import dosa.counselor.common.exception.CustomException;
import dosa.counselor.common.exception.ErrorCode;
import dosa.counselor.domain.user.Role;
import dosa.counselor.domain.user.User;
import dosa.counselor.domain.user.UserRepository;
import dosa.counselor.service.commonCode.CommonCodeService;
import dosa.counselor.web.dto.counselor.ChangePwRequestDto;
import dosa.counselor.web.dto.counselor.CounselorResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final CommonCodeService commonCodeService;


    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findById(email).orElse(null);
    }


    @Transactional
    public User saveOAuthUser(User user){
        return userRepository.save(user);
    }

    public CounselorResponseDto findByUserIdx(Long idx){
        User entity = userRepository.findByIdx(idx).orElseThrow(()->new RuntimeException("존재하지 않는 멤버"));

        return new CounselorResponseDto(entity, commonCodeService.findOneByValue("BANK", entity.getBank().longValue()).getName());

    }

    public User findByUserPk(Long idx) {
        return userRepository.findByIdx(idx).orElseThrow(()->new RuntimeException("존재하지 않는 멤버"));
    }

    public User findByLoginInfo(String email, String password) {
        Short[] status = {1,2};
        User user = userRepository.findTop1ByIdAndStatusIn(email,status).orElseThrow(()-> new BadCredentialsException("존재하지 않는 아이디입니다."));
        if (!BCrypt.getPasswordEncoder().matches(password, user.getPassword())) {
            throw new BadCredentialsException("비밀번호가 틀립니다");
        }

        return user;
    }

    public void updateCounselorPW(String email, ChangePwRequestDto dto) {
        User user = null;
        // 현재 비밀번호가 일치하는지 확인
        try{
            user = findByLoginInfo(email, dto.getCurrent());
        } catch (Exception e) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }

        if(user!=null){ // 새 비밀번호를 암호화하여 저장
            userRepository.updatePwById(BCrypt.getPasswordEncoder().encode(dto.getNewPw()), email);
        }


    }
}
