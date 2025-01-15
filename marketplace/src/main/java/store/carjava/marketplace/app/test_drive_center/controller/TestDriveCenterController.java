package store.carjava.marketplace.app.test_drive_center.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import store.carjava.marketplace.app.test_drive_center.dto.TestDriverCenterDetailInfoDto;
import store.carjava.marketplace.app.test_drive_center.dto.TestDriverCenterInfoListDto;
import store.carjava.marketplace.app.test_drive_center.service.TestDriveCenterService;

@RestController
@RequiredArgsConstructor
@Tag(name = "시승 센터 API", description = "시승 센터 관련 API를 제공합니다.")
public class TestDriveCenterController {

    private final TestDriveCenterService testDriveCenterService;

    @Operation(summary = "시승 센터 목록 조회", description = "모든 시승 센터의 정보를 조회합니다.")
    @GetMapping("/test-driver-centers")
    public ResponseEntity<TestDriverCenterInfoListDto> getAllTestDriveCenters() {
        TestDriverCenterInfoListDto response = testDriveCenterService.getAllTestDriveCenters();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "시승 센터 상세 정보 조회", description = "특정 시승 센터의 상세 정보를 조회합니다.")
    @GetMapping("/test-driver-centers/{testDriveCenterId}")
    public ResponseEntity<TestDriverCenterDetailInfoDto> getTestDriveCenterDetailInformation(
            @Parameter(description = "조회할 시승 센터 ID", required = true, example = "1")
            @PathVariable("testDriveCenterId") Long testDriveCenterId) {
        TestDriverCenterDetailInfoDto response = testDriveCenterService.getTestDriveCenterDetailInformation(testDriveCenterId);
        return ResponseEntity.ok(response);
    }
}
