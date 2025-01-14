package store.carjava.marketplace.app.test_drive_center.dto;

import store.carjava.marketplace.app.test_drive_center.entity.TestDriveCenter;

public record TestDriverCenterInfoDto(
        Long id,
        String name,
        String address,
        Double latitude,
        Double longitude
) {
    public static TestDriverCenterInfoDto of(TestDriveCenter testDriveCenter) {
        return new TestDriverCenterInfoDto(
                testDriveCenter.getId(),
                testDriveCenter.getName(),
                testDriveCenter.getAddress(),
                testDriveCenter.getLatitude(),
                testDriveCenter.getLongitude()
        );
    }
}
