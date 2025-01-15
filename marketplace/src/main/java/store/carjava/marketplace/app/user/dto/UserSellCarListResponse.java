package store.carjava.marketplace.app.user.dto;

import java.util.List;

public record UserSellCarListResponse(List<UserSellCarDto> userSellCarList) {
    public static UserSellCarListResponse of(List<UserSellCarDto> userSellCarList) {
        return new UserSellCarListResponse(userSellCarList);
    }
}
