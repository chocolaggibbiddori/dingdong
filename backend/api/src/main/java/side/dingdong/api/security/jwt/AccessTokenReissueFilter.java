package side.dingdong.api.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import side.dingdong.api.security.jwt.properties.JwtType;

@RequiredArgsConstructor
public class AccessTokenReissueFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final JwtAuthenticationConverter jwtAuthenticationConverter;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        SecurityContext securityContext = SecurityContextHolder.getContextHolderStrategy().getContext();
        Authentication authentication = securityContext.getAuthentication();
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        Claims accessTokenClaims = jwtAuthenticationToken.getClaims();
        String reissuedAccessToken = reissueAccessToken(request, accessTokenClaims);

        if (reissuedAccessToken == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT");
            return;
        }

        jwtAuthenticationToken.setToken(reissuedAccessToken);
        jwtAuthenticationToken.setAuthenticated(true);

        request.setAttribute(HttpHeaders.AUTHORIZATION, "Bearer " + reissuedAccessToken);
        response.setHeader(HttpHeaders.AUTHORIZATION, reissuedAccessToken);
        filterChain.doFilter(request, response);
    }

    private String reissueAccessToken(HttpServletRequest request, Claims accessTokenClaims) {
        String refreshToken = jwtAuthenticationConverter.convertRefreshToken(request);
        if (refreshToken == null) {
            return null;
        }

        try {
            Claims refreshTokenClaims = jwtService.validateAndExtractClaims(refreshToken, JwtType.REFRESH_TOKEN);
            jwtService.compareClaims(accessTokenClaims, refreshTokenClaims);
            return jwtService.reissueAccessToken(accessTokenClaims);
        } catch (JwtException e) {
            return null;
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        SecurityContext securityContext = SecurityContextHolder.getContextHolderStrategy().getContext();
        Authentication authentication = securityContext.getAuthentication();
        return authentication == null || authentication.isAuthenticated();
    }
}
