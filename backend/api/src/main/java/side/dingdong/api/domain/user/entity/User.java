package side.dingdong.api.domain.user.entity;

import com.github.f4b6a3.tsid.Tsid;
import com.github.f4b6a3.tsid.TsidCreator;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import side.dingdong.api.common.UserAuthority;
import side.dingdong.api.common.entity.BaseEntity;
import side.dingdong.api.common.entity.TsidType;

@Entity
@Table(name = "dingdong_user")
@Getter
@Setter
@ToString
public class User extends BaseEntity {

    @Id
    @TsidType
    private Tsid id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "authority", nullable = false)
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = UserAuthority.class)
    @CollectionTable(name = "user_authority", joinColumns = @JoinColumn(name = "user_id"))
    private Set<UserAuthority> authorities;

    @PrePersist
    public void prePersist() {
        this.id = TsidCreator.getTsid();
    }
}
