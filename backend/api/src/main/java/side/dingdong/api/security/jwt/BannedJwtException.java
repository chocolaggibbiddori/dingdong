package side.dingdong.api.security.jwt;

import io.jsonwebtoken.JwtException;

public class BannedJwtException extends JwtException {

    public BannedJwtException(String message) {
        super(message);
    }
}
