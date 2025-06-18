package side.dingdong.api.security.jwt.properties;

import lombok.Getter;

@Getter
public enum JwtType {

    ACCESS_TOKEN("at+jwt"),
    REFRESH_TOKEN("rt+jwt");

    private final String value;

    JwtType(String value) {
        this.value = value;
    }
}
