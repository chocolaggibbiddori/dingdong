package side.dingdong.api.security.jwt;

import com.github.f4b6a3.tsid.Tsid;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import side.dingdong.api.security.jwt.entity.RefreshToken;
import side.dingdong.api.security.jwt.entity.RefreshTokenId;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, RefreshTokenId> {

    @Query("SELECT r FROM RefreshToken r WHERE r.id.userId = ?1")
    List<RefreshToken> findByUserId(Tsid userId);
}
