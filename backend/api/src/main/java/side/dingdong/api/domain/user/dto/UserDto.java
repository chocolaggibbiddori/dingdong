package side.dingdong.api.domain.user.dto;

import com.github.f4b6a3.tsid.Tsid;
import java.util.HashSet;
import java.util.Set;
import lombok.Builder;
import side.dingdong.api.common.UserAuthority;

public record UserDto(
        Tsid id,
        String username,
        Set<UserAuthority> authorities
) {

    @Builder
    public UserDto {
        authorities = new HashSet<>(authorities);
    }

    @Override
    public Set<UserAuthority> authorities() {
        return new HashSet<>(authorities);
    }
}
