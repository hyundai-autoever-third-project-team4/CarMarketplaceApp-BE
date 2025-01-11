package store.carjava.marketplace.app.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(description = "리뷰 작성 요청 데이터")
public class ReviewCreateRequest {

    @Schema(description = "구매한 차 ID")
    String carId;

    @Schema(description = "리뷰 내용")
    @NotBlank(message = "리뷰 내용은 필수")
    String content;


    @Schema(description = "별점")
    Double starRate;

    @Schema(description = "차량 사진")
    String imageUrl;

}
