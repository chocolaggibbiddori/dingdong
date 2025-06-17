package side.dingdong.api.domain.user;

import com.github.f4b6a3.tsid.Tsid;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import side.dingdong.api.domain.user.dto.UserRegisterRequestDto;
import side.dingdong.api.domain.user.entity.User;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

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
}
