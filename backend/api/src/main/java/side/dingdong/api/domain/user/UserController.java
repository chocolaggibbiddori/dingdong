package side.dingdong.api.domain.user;

import com.github.f4b6a3.tsid.Tsid;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import side.dingdong.api.domain.user.dto.UserRegisterRequestDto;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> registerUser(@RequestBody @Valid UserRegisterRequestDto requestDto) {
        Tsid tsid = userService.registerUser(requestDto);
        URI location = URI.create("/api/v1/users/" + tsid.toString());
        return ResponseEntity.created(location).build();
    }
}
