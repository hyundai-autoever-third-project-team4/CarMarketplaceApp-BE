package store.carjava.marketplace.app.user.dto;

import store.carjava.marketplace.app.user.entity.User;

public record UserSummaryDto(
        Long id,
        String email,
        String role,
        String name,
        String phone,
        String address,
        int salesCount,
        int purchaseCount,
        int reviewCount,
        int reservationCount
) {
    public static UserSummaryDto of(User user) {
        return new UserSummaryDto(
                user.getId(),
                user.getEmail(),
                user.getRole(),
                user.getName(),
                user.getPhone(),
                user.getAddress(),
                user.getCarSalesHistories().size(),
                user.getCarPurchaseHistories().size(),
                user.getReviews().size(),
                user.getReservations().size()
        );
    }
}
