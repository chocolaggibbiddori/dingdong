package side.dingdong.api.security.jwt;

import com.github.f4b6a3.tsid.Tsid;
import java.util.Collection;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
@Builder
public class JwtCustomClaimsDto {

    private final Tsid userId;
    private final String username;
    private final Collection<? extends GrantedAuthority> authorities;
}
