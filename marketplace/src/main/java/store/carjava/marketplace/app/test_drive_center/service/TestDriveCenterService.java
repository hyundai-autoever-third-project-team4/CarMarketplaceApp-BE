package store.carjava.marketplace.app.test_drive_center.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.carjava.marketplace.app.test_drive_center.dto.TestDriverCenterInfoDto;
import store.carjava.marketplace.app.test_drive_center.dto.TestDriverCenterInfoListDto;
import store.carjava.marketplace.app.test_drive_center.entity.TestDriveCenter;
import store.carjava.marketplace.app.test_drive_center.repository.TestDriveCenterRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestDriveCenterService {

    private final TestDriveCenterRepository testDriveCenterRepository;

    public TestDriverCenterInfoListDto getAllTestDriveCenters() {

        // 1) 시승소 엔티티를 모두 조회
        List<TestDriveCenter> testDriveCenters = testDriveCenterRepository.findAll();

        // 2) 시승소 엔티티를 dto로 변환
        List<TestDriverCenterInfoDto> testDriverCenterInfoDtos = testDriveCenters.stream()
                .map(TestDriverCenterInfoDto::of).toList();

        return TestDriverCenterInfoListDto.of(testDriverCenterInfoDtos);
    }
}
