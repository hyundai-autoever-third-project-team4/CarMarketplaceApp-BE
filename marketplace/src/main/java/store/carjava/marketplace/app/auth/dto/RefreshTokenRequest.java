package store.carjava.marketplace.app.auth.dto;

public record RefreshTokenRequest(
        String refreshToken
) {

    public static RefreshTokenRequest of(String refreshToken) {
        return new RefreshTokenRequest(refreshToken);
    }
}
