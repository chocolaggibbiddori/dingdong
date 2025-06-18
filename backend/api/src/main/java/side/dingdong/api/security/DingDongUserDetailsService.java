package side.dingdong.api.security;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import side.dingdong.api.domain.user.UserRepository;
import side.dingdong.api.domain.user.entity.User;

@Service
@RequiredArgsConstructor
public class DingDongUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            throw new UsernameNotFoundException("No such user: " + username);
        }

        User user = userOpt.get();
        return new DingdongUserDetails(user.getId(), user.getUsername(), user.getAuthorities());
    }
}
