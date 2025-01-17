package store.carjava.marketplace.app.marketplace_car.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import store.carjava.marketplace.app.marketplace_car_extra_option.dto.MarketplaceCarExtraOptionInfoDto;
import store.carjava.marketplace.app.marketplace_car_image.dto.MarketplaceCarImageInfoDto;
import store.carjava.marketplace.app.marketplace_car_option.dto.marketplaceCarOptionInfoDto;
import store.carjava.marketplace.app.review.dto.ReviewCreateResponse;
import store.carjava.marketplace.app.vo.CarDetails;

import java.time.LocalDate;
import java.util.List;

@Builder
public record MarketplaceCarDetailPageResponse(
        @Schema(description = "마켓플레이스 차량 ID", example = "GGI241223011164")
        String id,

        @Schema(description = "차량 상세 정보")
        CarDetails carDetails,

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

        @Schema(description = "좋아요 갯수")
        Long LikeCount,

        @Schema(description = "로그인된 사용자의 찜 유무")
        Boolean islike,

        @Schema(description = "추가 옵션 정보 목록")
        List<MarketplaceCarExtraOptionInfoDto> carMarketplaceCarExtraOptionDtos,

        @Schema(description = "차량 이미지 정보 목록")
        List<MarketplaceCarImageInfoDto> marketplaceCarImageDtos,

        @Schema(description = "차량 기본 옵션 정보 목록")
        List<marketplaceCarOptionInfoDto> marketplaceCarOptionInfoDtos,

        @Schema(description = "차량 리뷰 정보 목록")
        List<ReviewCreateResponse> reviewCreateResponses

) {
}
