package side.dingdong.api.domain.user;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import side.dingdong.api.common.UserAuthority;
import side.dingdong.api.domain.user.dto.UserDto;
import side.dingdong.api.domain.user.dto.UserRegisterRequestDto;
import side.dingdong.api.domain.user.entity.User;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User mapToUser(UserRegisterRequestDto dto) {
        String username = dto.username();
        String encodedPassword = passwordEncoder.encode(dto.password());

        User user = new User();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setAuthorities(Set.of(UserAuthority.ROLE_USER));
        return user;
    }

    public UserDto mapToUserDto(User entity) {
        return UserDto.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .authorities(entity.getAuthorities())
                .build();
    }
}
