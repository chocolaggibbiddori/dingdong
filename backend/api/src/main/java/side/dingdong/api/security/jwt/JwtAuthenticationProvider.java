package side.dingdong.api.security.jwt;

import com.github.f4b6a3.tsid.Tsid;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import side.dingdong.api.common.UserAuthority;
import side.dingdong.api.security.DingdongUserDetails;
import side.dingdong.api.security.jwt.properties.JwtType;

@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtService jwtService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!(authentication instanceof JwtAuthenticationToken jwtAuthentication)) {
            return null;
        }

        String accessToken = jwtAuthentication.getCredentials().toString();

        try {
            Claims claims = jwtService.validateAndExtractClaims(accessToken, JwtType.ACCESS_TOKEN);
            return createJwtAuthenticationToken(claims, accessToken);
        } catch (ExpiredJwtException e) {
            Claims claims = e.getClaims();
            JwtAuthenticationToken jwtAuthenticationToken = createJwtAuthenticationToken(claims, accessToken);
            jwtAuthenticationToken.setAuthenticated(false);
            return jwtAuthenticationToken;
        } catch (BannedJwtException e) {
            throw new CredentialsExpiredException("JWT expired", e);
        } catch (MalformedJwtException e) {
            throw new BadCredentialsException("Invalid JWT", e);
        } catch (JwtException e) {
            throw new AuthenticationServiceException("Invalid JWT", e);
        }
    }

    private JwtAuthenticationToken createJwtAuthenticationToken(Claims claims, String token) {
        UserDetails userDetails = createUserDetails(claims);
        return new JwtAuthenticationToken(token, claims, userDetails, userDetails.getAuthorities());
    }

    private UserDetails createUserDetails(Claims claims) {
        String sub = claims.getSubject();
        Tsid tsid = Tsid.from(sub);
        String username = claims.get(JwtService.CLAIM_USERNAME_KEY, String.class);
        Set<UserAuthority> authorities = Set.of(claims.get(JwtService.CLAIM_AUTHORITIES_KEY, UserAuthority[].class));

        return new DingdongUserDetails(tsid, username, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
