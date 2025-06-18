package side.dingdong.api.domain.user;

import com.github.f4b6a3.tsid.Tsid;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import side.dingdong.api.domain.user.dto.UserDto;
import side.dingdong.api.domain.user.dto.UserRegisterRequestDto;
import side.dingdong.api.domain.user.entity.User;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public Tsid registerUser(UserRegisterRequestDto requestDto) {
        Optional<User> userOpt = userRepository.findByUsername(requestDto.username());
        if (userOpt.isPresent()) {
            throw new DuplicateKeyException("Username already in use");
        }

        User user = userMapper.mapToUser(requestDto);
        User saved = userRepository.save(user);
        return saved.getId();
    }

    public UserDto getUserDto(String username) throws EntityNotFoundException {
        User user = getCstnUser(username);
        return userMapper.mapToUserDto(user);
    }

    public boolean checkPassword(String username, String rawPassword) throws EntityNotFoundException {
        String encodedPassword = getCstnUser(username).getPassword();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    private User getCstnUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("No such user: " + username));
    }
}
