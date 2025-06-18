package side.dingdong.api.security.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;

@Slf4j
@RequiredArgsConstructor
public class JwtConfigurer extends AbstractHttpConfigurer<JwtConfigurer, HttpSecurity> {

    private final JwtService jwtService;
    private final JwtAuthenticationConverter jwtAuthenticationConverter;

    private RequestMatcher requestMatcher = PathPatternRequestMatcher.withDefaults().matcher("/api/v1/**");
    private SecurityContextRepository securityContextRepository;
    private AuthenticationSuccessHandler successHandler;
    private AuthenticationFailureHandler failureHandler;

    @Override
    public void init(HttpSecurity builder) {
        log.info("Init JwtConfigurer");
    }

    @Override
    public void configure(HttpSecurity builder) {
        JwtAuthenticationProvider provider = new JwtAuthenticationProvider(jwtService);
        builder.authenticationProvider(provider);

        AuthenticationManager manager = builder.getSharedObject(AuthenticationManager.class);
        AuthenticationFilter jwtAuthenticationFilter = getAuthenticationFilter(manager, jwtAuthenticationConverter);
        builder.addFilterBefore(jwtAuthenticationFilter, LogoutFilter.class);

        AccessTokenReissueFilter accessTokenReissueFilter = getAccessTokenReissueFilter();
        builder.addFilterBefore(accessTokenReissueFilter, LogoutFilter.class);
    }

    private AuthenticationFilter getAuthenticationFilter(AuthenticationManager manager,
                                                         AuthenticationConverter converter) {
        AuthenticationFilter jwtAuthenticationFilter = new AuthenticationFilter(manager, converter);
        jwtAuthenticationFilter.setRequestMatcher(requestMatcher);

        if (securityContextRepository != null) {
            jwtAuthenticationFilter.setSecurityContextRepository(securityContextRepository);
        }

        if (successHandler != null) {
            jwtAuthenticationFilter.setSuccessHandler(successHandler);
        }

        if (failureHandler != null) {
            jwtAuthenticationFilter.setFailureHandler(failureHandler);
        }

        return jwtAuthenticationFilter;
    }

    private AccessTokenReissueFilter getAccessTokenReissueFilter() {
        return new AccessTokenReissueFilter(jwtService, jwtAuthenticationConverter);
    }

    public JwtConfigurer requestMatcher(RequestMatcher requestMatcher) {
        Assert.notNull(requestMatcher, "requestMatcher cannot be null");
        this.requestMatcher = requestMatcher;
        return this;
    }

    public JwtConfigurer securityContextRepository(SecurityContextRepository securityContextRepository) {
        this.securityContextRepository = securityContextRepository;
        return this;
    }

    public JwtConfigurer successHandler(AuthenticationSuccessHandler successHandler) {
        this.successHandler = successHandler;
        return this;
    }

    public JwtConfigurer failureHandler(AuthenticationFailureHandler failureHandler) {
        this.failureHandler = failureHandler;
        return this;
    }
}
