package store.carjava.marketplace.firebase.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record GetTokenRequest(
        @Schema(description = "사용자 id")
        Long userId,

        @Schema(description = "fcm token")
        String token,

        @Schema(description = "생성된 시간")
        LocalDateTime currentTime
) {
}
