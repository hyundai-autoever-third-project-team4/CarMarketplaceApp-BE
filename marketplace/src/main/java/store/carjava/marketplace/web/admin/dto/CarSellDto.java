package store.carjava.marketplace.web.admin.dto;

import java.util.Map;
import store.carjava.marketplace.app.car_purchase_history.entity.CarPurchaseHistory;
import store.carjava.marketplace.app.car_sales_history.entity.CarSalesHistory;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CarSellDto(
        String carId,
        String carName,
        Long price,
        String userName,
        String licensePlate,
        String mainImage,
        LocalDate requestDate,
        String status,
        Long saleId

) {

    // 상태 설명 매핑
    private static final Map<String, String> STATUS_DESCRIPTIONS = Map.of(
            "AVAILABLE_FOR_PURCHASE", "구매 가능",
            "PENDING_PURCHASE_APPROVAL", "구매 승인 대기",
            "NOT_AVAILABLE_FOR_PURCHASE", "구매 불가",
            "PENDING_SALE", "판매 승인 대기",
            "SALE_APPROVED", "판매 승인"
    );

    // 상태 설명 반환
    private static String getStatusDescription(String status) {
        return STATUS_DESCRIPTIONS.getOrDefault(status, "알 수 없는 상태");
    }

    public static CarSellDto of(CarSalesHistory history) {
        return new CarSellDto(
                history.getMarketplaceCar().getId(),
                history.getMarketplaceCar().getCarDetails().getName(),
                history.getMarketplaceCar().getPrice(),
                history.getUser().getName(),
                history.getMarketplaceCar().getCarDetails().getLicensePlate(),
                history.getMarketplaceCar().getMainImage(),
                history.getMarketplaceCar().getMarketplaceRegistrationDate(),
                getStatusDescription(history.getMarketplaceCar().getStatus()),
                history.getId()
        );
    }
}
