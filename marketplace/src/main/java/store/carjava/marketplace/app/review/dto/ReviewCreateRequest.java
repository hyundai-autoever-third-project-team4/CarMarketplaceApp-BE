package store.carjava.marketplace.app.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "리뷰 작성 요청 데이터")
public class ReviewCreateRequest {

    @Schema(description = "리뷰 내용")
    String content;

    @Schema(description = "별점")
    Double starRate;


}
