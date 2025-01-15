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

        @Schema(description = "주행 거리 (킬로미터)", example = "22103")
        int mileage,

        @Schema(description = "차량 이름 (예: 소나타 )", example = "쏘나타")
        String name,

        @Schema(description = "판매 가격 (원 단위)", example = "43900000")
        Long price,

        @Schema(description = "마켓플레이스 등록일", example = "2023-01-01")
        LocalDate marketplaceRegistrationDate,

        @Schema(description = "메인 이미지 URL", example = "https://example.com/images/car-main.jpg")
        String mainImage,

        @Schema(description = "로그인된 사용자의 찜 유무")
        Boolean islike,

        @Schema(description = "좋아요 갯수")
        Long like

) {
}