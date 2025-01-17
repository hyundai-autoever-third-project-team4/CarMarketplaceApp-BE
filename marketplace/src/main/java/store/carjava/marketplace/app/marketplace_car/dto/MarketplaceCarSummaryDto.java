package store.carjava.marketplace.app.marketplace_car.dto;

import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;

import java.time.LocalDate;
import java.util.Map;

public record MarketplaceCarSummaryDto(
        String id,
        String mainImage,
        String name,
        String brand,
        String model,
        String licensePlate,
        LocalDate registrationDate,
        int mileage,
        Long price,
        String status // 상태 설명 필드
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

    // MarketplaceCar에서 DTO 생성
    public static MarketplaceCarSummaryDto of(MarketplaceCar marketplaceCar) {
        return new MarketplaceCarSummaryDto(
                marketplaceCar.getId(),
                marketplaceCar.getMainImage(),
                marketplaceCar.getCarDetails().getName(),
                marketplaceCar.getCarDetails().getBrand(),
                marketplaceCar.getCarDetails().getModel(),
                marketplaceCar.getCarDetails().getLicensePlate(),
                marketplaceCar.getCarDetails().getRegistrationDate(),
                marketplaceCar.getCarDetails().getMileage(),
                marketplaceCar.getPrice(), // price 필드 복구
                getStatusDescription(marketplaceCar.getStatus()) // 상태 변환 적용
        );
    }
}
