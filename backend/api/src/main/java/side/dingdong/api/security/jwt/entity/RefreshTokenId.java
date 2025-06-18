package side.dingdong.api.security.jwt.entity;

import com.github.f4b6a3.tsid.Tsid;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import side.dingdong.api.common.entity.TsidType;

@Data
@Embeddable
public class RefreshTokenId {

    @TsidType
    @Column(nullable = false)
    private Tsid userId;

    @Column(nullable = false, length = 36)
    private String jwtId;
}
