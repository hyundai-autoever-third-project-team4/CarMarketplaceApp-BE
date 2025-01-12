package store.carjava.marketplace.app.auth.dto;

public record TokenRequest(
        String authorizationCode
) {

    public static TokenRequest of(String authorizationCode) {
        return new TokenRequest(authorizationCode);
    }
}
