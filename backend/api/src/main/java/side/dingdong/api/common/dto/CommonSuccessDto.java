package side.dingdong.api.common.dto;

public record CommonSuccessDto(boolean success, String reason) {

    public CommonSuccessDto(boolean success) {
        this(success, null);
    }

    public static CommonSuccessDto createSuccessDto() {
        return new CommonSuccessDto(true);
    }

    public static CommonSuccessDto createFailDto(String reason) {
        return new CommonSuccessDto(false, reason);
    }
}
