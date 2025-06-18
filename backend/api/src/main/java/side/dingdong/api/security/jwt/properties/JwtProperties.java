package side.dingdong.api.security.jwt.properties;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Slf4j
@ConfigurationProperties("jwt")
public record JwtProperties(
        AccessToken accessToken,
        RefreshToken refreshToken
) {

    public JwtProperties {
        log.info("Init JwtProperties");
    }

    public record AccessToken(
            @DefaultValue("ACCESS_TOKEN") JwtType type,
            @DefaultValue("300000") long expirationTimeMillis,
            String secretKey
    ) implements JwtProperty {
    }

    public record RefreshToken(
            @DefaultValue("REFRESH_TOKEN") JwtType type,
            @DefaultValue("86400000") long expirationTimeMillis,
            String secretKey
    ) implements JwtProperty {
    }
}
