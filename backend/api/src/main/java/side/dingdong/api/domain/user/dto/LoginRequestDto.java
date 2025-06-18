package side.dingdong.api.domain.user.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
        @NotBlank
        String username,

        @NotBlank
        String password
) {
}
