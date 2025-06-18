package side.dingdong.api.security.jwt;

import com.github.f4b6a3.tsid.Tsid;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.impl.DefaultClock;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import side.dingdong.api.common.UserAuthority;
import side.dingdong.api.security.jwt.entity.RefreshToken;
import side.dingdong.api.security.jwt.entity.RefreshTokenId;
import side.dingdong.api.security.jwt.properties.JwtProperties;
import side.dingdong.api.security.jwt.properties.JwtProperties.AccessToken;
import side.dingdong.api.security.jwt.properties.JwtProperty;
import side.dingdong.api.security.jwt.properties.JwtType;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JwtService {

    public static final String CLAIM_USERNAME_KEY = "username";
    public static final String CLAIM_AUTHORITIES_KEY = "authorities";
    public static final String CLAIM_ISSUER_VALUE = "dingdong-api";

    private final JwtProperties jwtProperties;
    private final RefreshTokenRepository refreshTokenRepository;

    private SecretKey accessTokenSecretKey;
    private SecretKey refreshTokenSecretKey;

    @PostConstruct
    void init() {
        accessTokenSecretKey = generateSecretKey(jwtProperties.accessToken());
        refreshTokenSecretKey = generateSecretKey(jwtProperties.refreshToken());
    }

    private SecretKey generateSecretKey(JwtProperty jwtProperty) {
        return Keys.hmacShaKeyFor(jwtProperty.secretKey().getBytes());
    }

    /**
     * 주어진 클레임 정보를 기반으로 액세스 토큰을 생성합니다.
     *
     * @param jwtCustomClaimsDto JWT 토큰에 포함될 사용자 정의 클레임 정보를 담고 있는 객체
     * @return 생성된 액세스 토큰 문자열
     * @author chocola
     */
    public String generateAccessToken(JwtCustomClaimsDto jwtCustomClaimsDto) {
        AccessToken accessTokenProperties = jwtProperties.accessToken();
        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.getTime() + accessTokenProperties.expirationTimeMillis());

        return generateToken(jwtCustomClaimsDto, accessTokenProperties)
                .id(UUID.randomUUID().toString())
                .issuedAt(issuedAt)
                .expiration(expiration)
                .compact();
    }

    /**
     * 주어진 클레임 정보를 기반으로 리프레시 토큰을 생성합니다.
     *
     * @param jwtCustomClaimsDto JWT 토큰에 포함될 사용자 정의 클레임 정보를 담고 있는 객체
     * @return 생성된 리프레시 토큰 문자열
     * @author chocola
     */
    @Transactional
    public String generateRefreshToken(JwtCustomClaimsDto jwtCustomClaimsDto) {
        JwtProperties.RefreshToken refreshTokenProperties = jwtProperties.refreshToken();
        JwtBuilder jwtBuilder = generateToken(jwtCustomClaimsDto, refreshTokenProperties);

        String jti = UUID.randomUUID().toString();
        jwtBuilder.id(jti);

        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.getTime() + refreshTokenProperties.expirationTimeMillis());
        jwtBuilder.issuedAt(issuedAt);
        jwtBuilder.expiration(expiration);

        Tsid userId = jwtCustomClaimsDto.getUserId();
        RefreshTokenId refreshTokenId = new RefreshTokenId();
        refreshTokenId.setUserId(userId);
        refreshTokenId.setJwtId(jti);

        RefreshToken refreshTokenEntity = new RefreshToken();
        refreshTokenEntity.setId(refreshTokenId);
        refreshTokenEntity.setIssuedAt(issuedAt);
        refreshTokenEntity.setExpiredAt(expiration);
        refreshTokenEntity.setRevoked(false);

        List<RefreshToken> refreshTokenList = refreshTokenRepository.findByUserId(userId);
        refreshTokenList.forEach(refreshToken -> refreshToken.setRevoked(true));
        refreshTokenList.add(refreshTokenEntity);
        refreshTokenRepository.saveAll(refreshTokenList);

        return jwtBuilder.compact();
    }

    private JwtBuilder generateToken(JwtCustomClaimsDto jwtCustomClaimsDto, JwtProperty jwtProperty) {
        return getDefaultJwtBuilder(jwtProperty)
                .claims()
                .subject(jwtCustomClaimsDto.getUserId().toString())
                .add(CLAIM_USERNAME_KEY, jwtCustomClaimsDto.getUsername())
                .add(CLAIM_AUTHORITIES_KEY, jwtCustomClaimsDto.getAuthorities())
                .and();
    }

    private JwtBuilder getDefaultJwtBuilder(JwtProperty jwtProperty) {
        SecretKey secretKey = getSecretKey(jwtProperty.type());

        return Jwts.builder()
                .header()
                .type(jwtProperty.type().getValue())
                .and()

                .claims()
                .issuer(CLAIM_ISSUER_VALUE)
                .and()

                .signWith(secretKey, SIG.HS256);
    }

    private SecretKey getSecretKey(JwtType jwtType) {
        return switch (jwtType) {
            case ACCESS_TOKEN -> accessTokenSecretKey;
            case REFRESH_TOKEN -> refreshTokenSecretKey;
        };
    }

    Claims validateAndExtractClaims(String token, JwtType jwtType)
            throws MalformedJwtException, BannedJwtException, ExpiredJwtException {
        SecretKey secretKey = getSecretKey(jwtType);
        JwtParser jwtParser = Jwts
                .parser()
                .json(new JacksonDeserializer<>(Map.of(CLAIM_AUTHORITIES_KEY, UserAuthority[].class)))
                .requireIssuer(CLAIM_ISSUER_VALUE)
                .verifyWith(secretKey)
                .clockSkewSeconds(30)
                .clock(DefaultClock.INSTANCE)
                .build();

        Jws<Claims> jws = jwtParser.parseSignedClaims(token);
        validateType(jws, jwtType);

        Claims claims = jws.getPayload();
        String jti = claims.getId();
        String sub = claims.getSubject();
        Tsid userId = Tsid.from(sub);
        if (isBannedToken(userId, jti)) {
            throw new BannedJwtException("Invalid JWT");
        }

        return jws.getPayload();
    }

    boolean isBannedToken(Tsid userId, String jti) {
        RefreshTokenId refreshTokenId = new RefreshTokenId();
        refreshTokenId.setUserId(userId);
        refreshTokenId.setJwtId(jti);

        Optional<RefreshToken> refreshTokenOpt = refreshTokenRepository.findById(refreshTokenId);
        return refreshTokenOpt.isPresent() && refreshTokenOpt.get().isRevoked();
    }

    private void validateType(Jws<Claims> jws, JwtType jwtType) {
        if (!Objects.equals(jws.getHeader().getType(), jwtType.getValue())) {
            throw new MalformedJwtException("Invalid JWT");
        }
    }

    void compareClaims(Claims accessTokenClaims, Claims refreshTokenClaims) {
        boolean valid = equalsClaim(accessTokenClaims, refreshTokenClaims, Claims::getSubject) &&
                equalsClaim(accessTokenClaims, refreshTokenClaims, Claims::getIssuer) &&
                equalsClaim(accessTokenClaims, refreshTokenClaims,
                        claims -> claims.get(CLAIM_USERNAME_KEY, String.class));

        if (!valid) {
            throw new MalformedJwtException("Invalid JWT");
        }
    }

    private boolean equalsClaim(Claims accessTokenClaims, Claims refreshTokenClaims,
                                Function<Claims, String> function) {
        String accessTokenValue = function.apply(accessTokenClaims);
        String refreshTokenValue = function.apply(refreshTokenClaims);
        return Objects.equals(accessTokenValue, refreshTokenValue);
    }

    String reissueAccessToken(Claims claims) {
        return getDefaultJwtBuilder(jwtProperties.accessToken())
                .claims()
                .subject(claims.getSubject())
                .add(CLAIM_USERNAME_KEY, claims.get(CLAIM_USERNAME_KEY, String.class))
                .add(CLAIM_AUTHORITIES_KEY, claims.get(CLAIM_AUTHORITIES_KEY, String[].class))
                .and()
                .compact();
    }

    @Transactional
    public void banRefreshToken(Tsid userId) {
        List<RefreshToken> refreshTokenList = refreshTokenRepository.findByUserId(userId);
        refreshTokenList.forEach(refreshToken -> refreshToken.setRevoked(true));
        refreshTokenRepository.saveAll(refreshTokenList);
    }
}
