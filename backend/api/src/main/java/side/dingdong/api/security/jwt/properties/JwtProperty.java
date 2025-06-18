package side.dingdong.api.security.jwt.properties;

public interface JwtProperty {

    JwtType type();

    long expirationTimeMillis();

    String secretKey();
}
