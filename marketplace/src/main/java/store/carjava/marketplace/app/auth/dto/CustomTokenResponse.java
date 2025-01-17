package store.carjava.marketplace.app.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import store.carjava.marketplace.app.user.entity.User;

public record CustomTokenResponse(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("refresh_token") String refreshToken,
        Long userId,
        String email,
        String name,
        String phone,
        String address
) {
    public static CustomTokenResponse of(String accessToken, String refreshToken, User user) {
        return new CustomTokenResponse(
                accessToken,
                refreshToken,
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getPhone(),
                user.getAddress()
        );
    }
}
