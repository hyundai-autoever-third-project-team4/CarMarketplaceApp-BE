package store.carjava.marketplace.app.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UserProfileUpdateRequest(
        @NotBlank(message = "이름은 필수입니다")
        String name,

       @NotBlank(message = "전화번호는 필수입니다")
        String phone,

        @NotBlank(message = "주소는 필수입니다")
        String address
) {
}
