package side.dingdong.api.security.jwt;

import com.github.f4b6a3.tsid.Tsid;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import side.dingdong.api.security.DingdongUserDetails;

@RequiredArgsConstructor
public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {

    private final JwtService jwtService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) {
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);

        if (authentication == null) {
            return;
        }

        DingdongUserDetails userDetails = (DingdongUserDetails) authentication.getPrincipal();
        Tsid tsid = userDetails.getTsid();
        jwtService.banRefreshToken(tsid);
    }
}
