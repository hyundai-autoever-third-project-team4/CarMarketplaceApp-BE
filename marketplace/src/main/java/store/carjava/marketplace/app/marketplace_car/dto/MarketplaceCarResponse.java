package store.carjava.marketplace.app.marketplace_car.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import store.carjava.marketplace.app.car_purchase_history.dto.CarPurchaseHistoryInfoDto;
import store.carjava.marketplace.app.car_sales_history.dto.CarSalesHistoryInfoDto;
import store.carjava.marketplace.app.like.dto.LikeInfoDto;
import store.carjava.marketplace.app.marketplace_car_extra_option.dto.MarketplaceCarExtraOptionInfoDto;
import store.carjava.marketplace.app.marketplace_car_image.dto.MarketplaceCarImageInfoDto;
import store.carjava.marketplace.app.marketplace_car_option.dto.marketplaceCarOptionInfoDto;
import store.carjava.marketplace.app.reservation.dto.ReservationInfoDto;

import java.time.LocalDate;
import java.util.List;

@Builder
public record MarketplaceCarResponse(

        @Schema(description = "마켓플레이스 차량 ID", example = "GGI241223011164")
        String id,

        @Schema(description = "차량 상세 정보")
        MarketplaceCarDetailsDto carDetails,

        @Schema(description = "시승 센터 이름", example = "청주 시승소")
        String testDriveCenterName,

        @Schema(description = "판매 가격 (원 단위)", example = "43900000")
        Long price,

        @Schema(description = "마켓플레이스 등록일", example = "2023-01-01")
        LocalDate marketplaceRegistrationDate,

        @Schema(description = "차량 상태", example = "판매완료")
        String status,

        @Schema(description = "메인 이미지 URL", example = "https://example.com/images/car-main.jpg")
        String mainImage,

        @Schema(description = "예약 정보 목록")
        List<ReservationInfoDto> reservationDtos,

        @Schema(description = "좋아요 정보 목록")
        List<LikeInfoDto> likeDtos,

        @Schema(description = "차량 이미지 정보 목록")
        List<MarketplaceCarImageInfoDto> marketplaceCarImageDtos,

        @Schema(description = "추가 옵션 정보 목록")
        List<MarketplaceCarExtraOptionInfoDto> carMarketplaceCarExtraOptionDtos,

        @Schema(description = "구매 이력 정보 목록")
        List<CarPurchaseHistoryInfoDto> carPurchaseHistoryInfoDtos,

        @Schema(description = "판매 이력 정보 목록")
        List<CarSalesHistoryInfoDto> carSalesHistoryInfoDtos,

        @Schema(description = "차량 기본 옵션 정보 목록")
        List<marketplaceCarOptionInfoDto> marketplaceCarOptionInfoDtos
) {
}