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
import side.dingdong.api.domain.entity.BaseEntity;
import side.dingdong.api.domain.entity.TsidType;
import side.dingdong.api.common.UserRole;

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

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = UserRole.class)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    private Set<UserRole> roles;

    @PrePersist
    public void prePersist() {
        this.id = TsidCreator.getTsid();
    }
}
