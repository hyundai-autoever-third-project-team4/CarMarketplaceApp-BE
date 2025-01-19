package store.carjava.marketplace.app.marketplace_car.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record MarketplaceCarDetailsDto(


        @Schema(description = "차량 브랜드 이름", example = "현대")
        String brand,

        @Schema(description = "차량 모델 이름", example = "소나타")
        String model,

        @Schema(description = "구동 방식 (예: 전륜, 후륜, 4륜구동)", example = "AWD")
        String driveType,

        @Schema(description = "엔진 배기량 (cc)", example = "2000")
        Integer engineCapacity,

        @Schema(description = "외부 색상", example = "세레니티 화이트 펄")
        String exteriorColor,

        @Schema(description = "내부 색상", example = "블랙모노톤")
        String interiorColor,

        @Schema(description = "색상 종류", example = "블랙")
        String colorType,

        @Schema(description = "연료 타입 (예: 가솔린, 디젤, 전기)", example = "가솔린")
        String fuelType,

        @Schema(description = "차량 번호판", example = "186수2468")
        String licensePlate,

        @Schema(description = "주행 거리 (킬로미터)", example = "22103")
        int mileage,

        @Schema(description = "모델 연도", example = "2022")
        int modelYear,

        @Schema(description = "차량 이름 (예: 소나타 )", example = "쏘나타")
        String name,

        @Schema(description = "좌석 수", example = "5")
        int seatingCapacity,

        @Schema(description = "변속기 유형 (예: 자동, 수동)", example = "자동")
        String transmission,

        @Schema(description = "차량 종류 (예: 세단, SUV)", example = "승용")
        String vehicleType,

        @Schema(description = "등록 날짜", example = "2023-01-01")
        LocalDate registrationDate
) {
}