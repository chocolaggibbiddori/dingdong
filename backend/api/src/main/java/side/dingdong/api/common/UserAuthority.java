package side.dingdong.api.common;

import org.springframework.security.core.GrantedAuthority;

public enum UserAuthority implements GrantedAuthority {

    ROLE_USER,
    ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
