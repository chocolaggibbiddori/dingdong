package side.dingdong.api.security.jwt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import side.dingdong.api.common.entity.BaseEntity;

@Entity
@Getter
@Setter
@ToString
public class RefreshToken extends BaseEntity {

    @EmbeddedId
    private RefreshTokenId id;

    @Column(nullable = false)
    private Date issuedAt;

    @Column(nullable = false)
    private Date expiredAt;

    @Column(nullable = false)
    private boolean revoked;
}
