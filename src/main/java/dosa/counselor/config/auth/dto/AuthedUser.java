package dosa.counselor.config.auth.dto;

import dosa.counselor.domain.user.Role;
import dosa.counselor.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class AuthedUser implements Serializable {
    private Long idx;
    private Role role;
    private String name;
    private String email;
    private String id;

    public AuthedUser(User user) {
        this.idx = user.getIdx();
        this.role = Role.COUNSELOR;
        this.name = user.getName();
        this.email = user.getEmail();
        this.id = user.getId();
    }

}