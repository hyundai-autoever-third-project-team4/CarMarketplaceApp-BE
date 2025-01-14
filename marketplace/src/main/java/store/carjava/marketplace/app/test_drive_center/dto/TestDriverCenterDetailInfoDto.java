package store.carjava.marketplace.app.test_drive_center.dto;

import store.carjava.marketplace.app.marketplace_car.dto.MarketplaceCarSummaryDto;
import store.carjava.marketplace.app.test_drive_center.entity.TestDriveCenter;

import java.util.List;

public record TestDriverCenterDetailInfoDto(
        Long id,
        String name,
        String address,
        Double latitude,
        Double longitude,
        List<MarketplaceCarSummaryDto> marketplaceCarSummaryDtos
) {
    public static TestDriverCenterDetailInfoDto of(TestDriveCenter testDriverCenter, List<MarketplaceCarSummaryDto> marketplaceCarSummaryDtos) {
        return new TestDriverCenterDetailInfoDto(
                testDriverCenter.getId(),
                testDriverCenter.getName(),
                testDriverCenter.getAddress(),
                testDriverCenter.getLatitude(),
                testDriverCenter.getLongitude(),
                marketplaceCarSummaryDtos
        );
    }
}
