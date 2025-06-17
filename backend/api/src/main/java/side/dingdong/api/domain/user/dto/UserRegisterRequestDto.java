package side.dingdong.api.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRegisterRequestDto(
        @NotBlank
        String username,

        @NotBlank
        @Size(min = 8, message = "8글자 이상이어야 합니다.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+=\\-{}\\[\\]:;\"'<>,.?/~`]).+$",
                message = "알파벳 + 숫자 + 특수문자 조합이어야 합니다.")
        String password
) {
}
