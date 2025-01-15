package store.carjava.marketplace.app.marketplace_car.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record MarketplaceCarSendToManagerDto(
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
        String mainImage

) {
}
