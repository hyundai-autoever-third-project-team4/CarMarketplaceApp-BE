package store.carjava.marketplace.app.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CustomTokenResponse(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("refresh_token") String refreshToken,
        Long userId,
        String email
) {
}
