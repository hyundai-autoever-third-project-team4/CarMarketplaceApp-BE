package store.carjava.marketplace.app.car_model_grade;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record MarketplaceCarRecommandListResponse(
        @Schema(description = "추천 결과 (적정, 저렴, 초과)")
        List<MarketplaceCarRecommandInfoDto> recommandResults
) {
    public static MarketplaceCarRecommandListResponse of(List<MarketplaceCarRecommandInfoDto> recommandDtos) {
        return new MarketplaceCarRecommandListResponse(recommandDtos);
    }
}
