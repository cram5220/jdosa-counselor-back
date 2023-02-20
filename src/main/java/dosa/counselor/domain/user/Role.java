package dosa.counselor.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    GUEST("ROLE_GUEST","비회원"),
    MEMBER("ROLE_MEMBER","회원"),
    COUNSELOR("ROLE_COUNSELOR","상담사"),
    MANAGER("ROLE_MANAGER","운영자"),
    ADMIN("ROLE_ADMIN","관리자");

    private final String key;
    private final String title;
}
