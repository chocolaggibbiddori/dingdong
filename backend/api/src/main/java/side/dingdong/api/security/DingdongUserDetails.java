package side.dingdong.api.security;

import com.github.f4b6a3.tsid.Tsid;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

@Getter
@ToString
@EqualsAndHashCode
public class DingdongUserDetails implements UserDetails {

    private final Tsid tsid;
    private final String username;
    private final Set<GrantedAuthority> authorities;

    public DingdongUserDetails(Tsid tsid, String username, Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(tsid, "Tsid cannot be null");
        Assert.hasText(username, "Username cannot be empty");
        Assert.notNull(authorities, "Authorities cannot be null");
        this.tsid = tsid;
        this.username = username;
        this.authorities = Set.copyOf(authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new HashSet<>(authorities);
    }

    @Override
    public String getPassword() {
        return null;
    }
}
