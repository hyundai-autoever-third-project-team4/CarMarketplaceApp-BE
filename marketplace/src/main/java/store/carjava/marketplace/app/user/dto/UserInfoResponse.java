package store.carjava.marketplace.app.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import store.carjava.marketplace.app.user.entity.User;

public record UserInfoResponse(
        @Schema(description = "유저 id", example = "1")
        Long id,
        @Schema(description = "유저 이메일", example = "abc@naver.com")
        String email,
        @Schema(description = "이름", example = "홍길동")
        String name,
        @Schema(description = "유저 전화번호", example = "010-1234-1234")
        String phone,
        @Schema(description = "주소", example = "금천구 가산동...")
        String address,
        @Schema(description = "역할", example = "ROLE_ADMIN")
        String role
) {
    public static UserInfoResponse of(User user) {
        return new UserInfoResponse(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getPhone(),
                user.getAddress(),
                user.getRole()

        );
    }
}
