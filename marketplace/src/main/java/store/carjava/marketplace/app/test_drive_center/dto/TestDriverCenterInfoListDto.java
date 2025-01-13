package store.carjava.marketplace.app.test_drive_center.dto;


import java.util.List;

public record TestDriverCenterInfoListDto(
        List<TestDriverCenterInfoDto> testDriverCenterInfoDtos
) {
    public static TestDriverCenterInfoListDto of(List<TestDriverCenterInfoDto> testDriverCenterInfoDtos) {
        return new TestDriverCenterInfoListDto(testDriverCenterInfoDtos);
    }
}
