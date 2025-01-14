package store.carjava.marketplace.app.test_drive_center.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.carjava.marketplace.app.marketplace_car.dto.MarketplaceCarSummaryDto;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;
import store.carjava.marketplace.app.marketplace_car.repository.MarketplaceCarRepository;
import store.carjava.marketplace.app.test_drive_center.dto.TestDriverCenterDetailInfoDto;
import store.carjava.marketplace.app.test_drive_center.dto.TestDriverCenterInfoDto;
import store.carjava.marketplace.app.test_drive_center.dto.TestDriverCenterInfoListDto;
import store.carjava.marketplace.app.test_drive_center.entity.TestDriveCenter;
import store.carjava.marketplace.app.test_drive_center.exception.TestDriverCenterIdNotFoundException;
import store.carjava.marketplace.app.test_drive_center.repository.TestDriveCenterRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestDriveCenterService {

    private final TestDriveCenterRepository testDriveCenterRepository;
    private final MarketplaceCarRepository marketplaceCarRepository;

    public TestDriverCenterInfoListDto getAllTestDriveCenters() {

        // 1) 시승소 엔티티를 모두 조회
        List<TestDriveCenter> testDriveCenters = testDriveCenterRepository.findAll();

        // 2) 시승소 엔티티를 dto로 변환
        List<TestDriverCenterInfoDto> testDriverCenterInfoDtos = testDriveCenters.stream()
                .map(TestDriverCenterInfoDto::of).toList();

        return TestDriverCenterInfoListDto.of(testDriverCenterInfoDtos);
    }

    public TestDriverCenterDetailInfoDto getTestDriveCenterDetailInformation(Long testDriveCenterId) {

        // 1) testDriverCenterId에 해당하는 test driver center 조회
        TestDriveCenter testDriveCenter = testDriveCenterRepository.findById(testDriveCenterId)
                .orElseThrow(TestDriverCenterIdNotFoundException::new);

        // 2) 시승소 id 에 해당하는 marketplace car 엔티티를 모두 조회
        List<MarketplaceCar> marketplaceCars = marketplaceCarRepository.findAllByTestDriveCenter(testDriveCenter);

        // 3) marketplace car 엔티티를 summary dto list로 변환
        List<MarketplaceCarSummaryDto> marketplaceCarSummaryDtos = marketplaceCars.stream()
                .map(MarketplaceCarSummaryDto::of).toList();

        // 4) test driver entity와 summary list dto를 병합하여 response dto 생성하여 리턴
        return TestDriverCenterDetailInfoDto.of(testDriveCenter, marketplaceCarSummaryDtos);
    }
}
