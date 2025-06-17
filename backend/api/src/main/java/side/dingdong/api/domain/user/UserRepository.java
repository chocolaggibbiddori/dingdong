package side.dingdong.api.domain.user;

import com.github.f4b6a3.tsid.Tsid;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import side.dingdong.api.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Tsid> {

    Optional<User> findByUsername(String username);
}
