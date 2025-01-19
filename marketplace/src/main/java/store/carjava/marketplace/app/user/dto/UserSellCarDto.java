package store.carjava.marketplace.app.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public record UserSellCarDto(

        @Schema(description = "차 id", example = "GJU241125010629")
        String id,

        @Schema(description = "차량이름", example = "2021G80 가솔린 2.5 터보 2WD 시그니처 디자인 셀렉션Ⅱ")
        String carName,

        @Schema(description = "차량번호판", example = "183무1416")
        String licensePlate,

        @Schema(description = "차량등록일", example = "2020-09-10")
        LocalDate registrationDate,

        @Schema(description = "주행거리", example = "350000")
        int mileage,

        @Schema(description = "가격", example = "4500000")
        Long price,

        @Schema(description = "차량사진", example = "https://certified-static.hyundai.com/contents/goods/shootConts/tobepic/02/exterior/GGK241216011103/PRD602_233.JPG/dims/crop/2304x1536+600+770/resize/380x253/optimize")
        String mainImage,

        @Schema(description = "차량상태", example = "PENDING_SALE")
        String status


) {
}
