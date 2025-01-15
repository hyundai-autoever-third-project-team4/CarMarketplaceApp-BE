package store.carjava.marketplace.app.marketplace_car.dto;


import lombok.Builder;

@Builder
public record MarketplaceCarRegisterRequest(
        String licensePlate, // 차량 번호판
        String ownerName // 차량 소유자 이름
) {
}
