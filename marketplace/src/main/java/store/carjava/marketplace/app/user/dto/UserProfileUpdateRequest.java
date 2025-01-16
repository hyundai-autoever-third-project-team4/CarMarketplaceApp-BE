package store.carjava.marketplace.app.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record UserProfileUpdateRequest(
        @Schema(description = "유저 이름")
        @NotBlank(message = "이름은 필수입니다")
        String name,

        @Schema(description = "유저 전화번호")
        @NotBlank(message = "전화번호는 필수입니다")
        String phone,

        @Schema(description = "유저 주소")
        @NotBlank(message = "주소는 필수입니다")
        String address
) {
}
