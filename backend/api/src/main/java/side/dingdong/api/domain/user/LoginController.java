package side.dingdong.api.domain.user;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import side.dingdong.api.domain.CommonSuccessDto;
import side.dingdong.api.domain.user.dto.LoginRequestDto;
import side.dingdong.api.domain.user.dto.UserDto;
import side.dingdong.api.security.jwt.JwtCustomClaimsDto;
import side.dingdong.api.security.jwt.JwtService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<CommonSuccessDto> login(@RequestBody @Valid LoginRequestDto dto) {
        String username = dto.username();
        String password = dto.password();
        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.ok();

        try {
            boolean passwordMatches = userService.checkPassword(username, password);

            if (passwordMatches) {
                JwtCustomClaimsDto jwtCustomClaimsDto = getJwtCustomClaimsDto(username);
                String accessToken = jwtService.generateAccessToken(jwtCustomClaimsDto);
                String refreshToken = jwtService.generateRefreshToken(jwtCustomClaimsDto);
                ResponseCookie refreshTokenCookie = getRefreshTokenCookie(refreshToken);

                responseBuilder.header(HttpHeaders.AUTHORIZATION, accessToken);
                responseBuilder.header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
                return responseBuilder.body(CommonSuccessDto.createSuccessDto());
            } else {
                return responseBuilder.body(CommonSuccessDto.createFailDto("password"));
            }
        } catch (EntityNotFoundException ignored) {
            return responseBuilder.body(CommonSuccessDto.createFailDto("username"));
        }
    }

    private JwtCustomClaimsDto getJwtCustomClaimsDto(String username) {
        UserDto userDto = userService.getUserDto(username);
        return JwtCustomClaimsDto.builder()
                .userId(userDto.id())
                .username(userDto.username())
                .authorities(userDto.authorities())
                .build();
    }

    private ResponseCookie getRefreshTokenCookie(String refreshToken) {
        return ResponseCookie
                .from("refresh-token", refreshToken)
                .secure(false)
                .httpOnly(true)
                .path("/api/v1")
                .sameSite("Strict")
                .maxAge(-1L)
                .build();
    }
}
