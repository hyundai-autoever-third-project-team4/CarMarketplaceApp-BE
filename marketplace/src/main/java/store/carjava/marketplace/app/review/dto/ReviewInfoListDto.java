package store.carjava.marketplace.app.review.dto;

import java.util.List;

public record ReviewInfoListDto (List<ReviewInfoDto> reviewInfoList) {
    public static ReviewInfoListDto of(List<ReviewInfoDto> reviewInfoList) {
        return new ReviewInfoListDto(reviewInfoList);
    }
}
