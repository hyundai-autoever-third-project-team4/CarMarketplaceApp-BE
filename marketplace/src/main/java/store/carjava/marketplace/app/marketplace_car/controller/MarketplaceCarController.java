package store.carjava.marketplace.app.marketplace_car.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import store.carjava.marketplace.app.marketplace_car.dto.MarketplaceCarResponse;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;
import store.carjava.marketplace.app.marketplace_car.service.MarketplaceCarService;

import java.util.List;

@Tag(name = "MarketplaceCar", description = "마켓플레이스 차량 관련 API")
@RestController
@RequiredArgsConstructor
public class MarketplaceCarController {

    private final MarketplaceCarService marketplaceCarService;

    @Operation(summary = "모든 차량 조회", description = "마켓플레이스에 등록된 모든 차량 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공적으로 차량 정보를 반환했습니다."),
            @ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
    })
    @GetMapping("/api-docs/car")
    public List<MarketplaceCarResponse> getAllCar() {
        return marketplaceCarService.getAllCars();
    }


    @Operation(summary = "차량 필터별 조회", description = "필터 조건에 맞는 차량 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공적으로 차량 정보를 반환했습니다."),
            @ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
    })
    @GetMapping("/filter")
    public ResponseEntity<List<MarketplaceCarResponse>> filterCars(
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String colorType,
            @RequestParam(required = false) String driveType,
            @RequestParam(required = false) String licensePlate,
            @RequestParam(required = false) String transmission,
            @RequestParam(required = false) String vehicleType,
            @RequestParam(required = false) String testDriveCenterName,
            @RequestParam(required = false) Integer modelYear,
            @RequestParam(required = false) Integer seatingCapacity,
            @RequestParam(required = false) String fuelType,
            @RequestParam(required = false) Long maxPrice,
            @RequestParam(required = false) Integer minMileage,
            @RequestParam(required = false) Integer maxMileage,
            @RequestParam(required = false) Integer minModelYear,
            @RequestParam(required = false) Integer maxModelYear,
            @RequestParam(required = false) Long minPrice,
            @RequestParam(required = false) List<Long> optionIds, // 추가된 부분
            @RequestParam(required = false) Integer maxEngineCapacity,
            @RequestParam(required = false) Integer minEngineCapacity,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "인기순") String sortOrder // 정렬 옵션 추가 (기본값: 인기순)
    ) {
        List<MarketplaceCarResponse> filteredCars = marketplaceCarService.getFilteredCars(
                model, fuelType, brand, colorType,
                driveType, licensePlate,transmission,vehicleType,
                modelYear,seatingCapacity, maxPrice, minPrice, minMileage,
                 maxMileage,  minModelYear,  maxModelYear, optionIds,
                testDriveCenterName, status, maxEngineCapacity, minEngineCapacity,
                name, sortOrder
        );
        return ResponseEntity.ok(filteredCars);
    }
}