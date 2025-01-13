package store.carjava.marketplace.app.test_drive_center.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import store.carjava.marketplace.app.test_drive_center.dto.TestDriverCenterInfoListDto;
import store.carjava.marketplace.app.test_drive_center.service.TestDriveCenterService;

@RestController
@RequiredArgsConstructor
public class TestDriveCenterController {

    private final TestDriveCenterService testDriveCenterService;

    @GetMapping("/test-driver-centers")
    public ResponseEntity<?> getAllTestDriveCenters() {
        TestDriverCenterInfoListDto response = testDriveCenterService.getAllTestDriveCenters();

        return ResponseEntity.ok(response);
    }

}
