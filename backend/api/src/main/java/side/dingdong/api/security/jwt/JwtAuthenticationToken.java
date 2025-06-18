package side.dingdong.api.security.jwt;

import io.jsonwebtoken.Claims;
import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;
    private final Claims claims;
    private String token;

    public JwtAuthenticationToken(String token) {
        super(null);
        this.token = token;
        this.claims = null;
        this.principal = null;
    }

    public JwtAuthenticationToken(String token, Claims claims, Object principal,
                                  Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.token = token;
        this.claims = claims;
        this.principal = principal;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.token;
    }
}
