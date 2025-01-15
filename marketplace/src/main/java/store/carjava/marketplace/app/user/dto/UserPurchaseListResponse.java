package store.carjava.marketplace.app.user.dto;

import java.util.List;

public record UserPurchaseListResponse(List<UserPurchaseCarDto> userPurchaseCarList) {
    public static UserPurchaseListResponse of(List<UserPurchaseCarDto> userPurchaseCarList) {
        return new UserPurchaseListResponse(userPurchaseCarList);
    }
}
