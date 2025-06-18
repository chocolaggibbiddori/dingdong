package side.dingdong.api.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.NullSecurityContextRepository;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatchers;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import side.dingdong.api.security.jwt.JwtAuthenticationConverter;
import side.dingdong.api.security.jwt.JwtConfigurer;
import side.dingdong.api.security.jwt.JwtLogoutSuccessHandler;
import side.dingdong.api.security.jwt.JwtService;

@Configuration
@RequiredArgsConstructor
class SecurityConfig {

    private final JwtService jwtService;
    private final JwtAuthenticationConverter jwtAuthenticationConverter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(CsrfConfigurer::disable)

                .cors(cors -> cors
                        .configurationSource(corsConfigurationSource()))

                .with(new JwtConfigurer(jwtService, jwtAuthenticationConverter), jwt -> jwt
                        .requestMatcher(jwtAuthenticationRequestMatcher())
                        .successHandler((request, response, authentication) -> {
                        })
                        .securityContextRepository(new NullSecurityContextRepository()))

                .logout(logout -> logout
                        .logoutRequestMatcher(
                                PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.POST, "/api/v1/logout"))
                        .clearAuthentication(true)
                        .deleteCookies("refresh-token")
                        .invalidateHttpSession(true)
                        .logoutSuccessHandler(new JwtLogoutSuccessHandler(jwtService)))

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(request -> request
                        .requestMatchers(permitAllRequestMatcher()).permitAll()
                        .anyRequest().authenticated())

                .build();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.addExposedHeader(HttpHeaders.AUTHORIZATION);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private RequestMatcher permitAllRequestMatcher() {
        return RequestMatchers.anyOf(
                CorsUtils::isPreFlightRequest,
                PathPatternRequestMatcher.withDefaults().matcher("/error/**"),
                PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.POST, "/api/v1/users"),
                PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.POST, "/api/v1/login"));
    }

    private RequestMatcher jwtAuthenticationRequestMatcher() {
        return RequestMatchers.allOf(
                RequestMatchers.not(permitAllRequestMatcher()),
                PathPatternRequestMatcher.withDefaults().matcher("/api/v1/**"));
    }
}
