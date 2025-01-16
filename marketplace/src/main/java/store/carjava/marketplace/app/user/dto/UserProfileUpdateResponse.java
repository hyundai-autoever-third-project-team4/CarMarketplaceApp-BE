package store.carjava.marketplace.app.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import store.carjava.marketplace.app.user.entity.User;

public record UserProfileUpdateResponse(
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
        @Schema(description = "메시지", example = "유저 정보가 성공적으로 업데이트되었습니다.")
        String message

) {
    public static UserProfileUpdateResponse of(User user) {
        return new UserProfileUpdateResponse(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getPhone(),
                user.getAddress(),
                "유저 정보가 성공적으로 업데이트되었습니다"

        );
    }
}
