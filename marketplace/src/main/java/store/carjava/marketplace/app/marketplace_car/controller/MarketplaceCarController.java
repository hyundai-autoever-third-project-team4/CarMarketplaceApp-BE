package store.carjava.marketplace.app.marketplace_car.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import store.carjava.marketplace.app.base_car.dto.BaseCarResponse;
import store.carjava.marketplace.app.base_car.entity.BaseCar;
import store.carjava.marketplace.app.marketplace_car.dto.*;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import store.carjava.marketplace.app.marketplace_car.dto.MarketplaceCarResponse;

import store.carjava.marketplace.app.marketplace_car.service.MarketplaceCarService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Tag(name = "마켓플레이스 (중고차) API", description = "마켓플레이스 차량 관련 API")
@RestController
@RequiredArgsConstructor
public class MarketplaceCarController {

    private final MarketplaceCarService marketplaceCarService;

    @Operation(summary = "모든 차량 조회", description = "마켓플레이스에 등록된 모든 차량 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공적으로 차량 정보를 반환했습니다."),
            @ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
    })
    @GetMapping("/cars")
    public List<MarketplaceCarResponse> getAllCar() {
        return marketplaceCarService.getAllCars();
    }

    @Operation(summary = "차량 필터별 조회", description = "필터 조건에 맞는 차량 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공적으로 차량 정보를 반환했습니다."),
            @ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
    })
    @GetMapping("/cars/search")
    public ResponseEntity<List<MarketplaceCarResponse>> filterCars(
            @Parameter(description = "차량 모델") @RequestParam(name = "models", required = false) List<String> models,
            @Parameter(description = "브랜드") @RequestParam(name = "brand", required = false) String brand,
            @Parameter(description = "색상") @RequestParam(name = "colorTypes", required = false) List<String> colorTypes,
            @Parameter(description = "차량 구동 방식") @RequestParam(name = "driveType", required = false) String driveType,
            @Parameter(description = "번호판") @RequestParam(name = "licensePlate", required = false) String licensePlate,
            @Parameter(description = "변속기 종류") @RequestParam(name = "transmission", required = false) String transmission,
            @Parameter(description = "차량 종류") @RequestParam(name = "vehicleTypes", required = false) List<String> vehicleTypes,
            @Parameter(description = "시승소 이름") @RequestParam(name = "testDriveCenterName", required = false) String testDriveCenterName,
            @Parameter(description = "모델 연식") @RequestParam(name = "modelYear", required = false) Integer modelYear,
            @Parameter(description = "인승 수") @RequestParam(name = "seatingCapacity", required = false) Integer seatingCapacity,
            @Parameter(description = "연료 타입") @RequestParam(name = "fuelTypes", required = false) List<String> fuelTypes,
            @Parameter(description = "최대 가격") @RequestParam(name = "maxPrice", required = false) Long maxPrice,
            @Parameter(description = "최소 가격") @RequestParam(name = "minPrice", required = false) Long minPrice,
            @Parameter(description = "최대 주행거리") @RequestParam(name = "maxMileage", required = false) Integer maxMileage,
            @Parameter(description = "최소 주행거리") @RequestParam(name = "minMileage", required = false) Integer minMileage,
            @Parameter(description = "최소 모델 연식") @RequestParam(name = "minModelYear", required = false) Integer minModelYear,
            @Parameter(description = "최대 모델 연식") @RequestParam(name = "maxModelYear", required = false) Integer maxModelYear,
            @Parameter(description = "옵션 리스트") @RequestParam(name = "optionIds", required = false) List<Long> optionIds,
            @Parameter(description = "최대 배기량") @RequestParam(name = "maxEngineCapacity", required = false) Integer maxEngineCapacity,
            @Parameter(description = "최소 배기량") @RequestParam(name = "minEngineCapacity", required = false) Integer minEngineCapacity,
            @Parameter(description = "차량 상태") @RequestParam(name = "status", required = false) String status,
            @Parameter(description = "차량 이름") @RequestParam(name = "name", required = false) String name,
            @Parameter(description = "정렬 옵션") @RequestParam(name = "sortOrder", defaultValue = "인기순") String sortOrder,
            @Parameter(description = "페이지네이션 설정") @PageableDefault(size = 10, page = 0) Pageable pageable
    ) {
        List<MarketplaceCarResponse> filteredCars = marketplaceCarService.getFilteredCars(
                models, fuelTypes, brand, colorTypes,
                driveType, licensePlate, transmission, vehicleTypes,
                modelYear, seatingCapacity, maxPrice, minPrice, minMileage,
                maxMileage, minModelYear, maxModelYear, optionIds,
                testDriveCenterName, status, maxEngineCapacity, minEngineCapacity,
                name, sortOrder, pageable
        );
        return ResponseEntity.ok(filteredCars);
    }

    @Operation(summary = "차량 등록", description = "새로운 차량을 등록합니다.")
    @PostMapping("/car/register")
    public ResponseEntity<String> registerCar(@RequestBody MarketplaceCarRegisterRequest request) {
        try {
            marketplaceCarService.sellRegisterCar(request);
            return ResponseEntity.ok("판매차량 등록 성공!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("서버 오류가 발생했습니다. 나중에 다시 시도해주세요.");
        }
    }

    @Operation(summary = "차량 상태별 조회", description = "특정 상태의 차량 목록을 조회합니다.")
    @GetMapping("/cars/search/status")
    public ResponseEntity<List<MarketplaceCarSendToManagerDto>> getCarsByStatus(
            @RequestParam(name = "status", defaultValue = "AVAILABLE_FOR_PURCHASE") String status) {
        List<MarketplaceCarSendToManagerDto> cars = marketplaceCarService.getCarsByStatus(status);
        return ResponseEntity.ok(cars);
    }

    @Operation(summary = "최종 차량 판매 완료", description = "차량의 최종 판매를 완료합니다.")
    @PutMapping("/car/sale/complete-sale")
    public ResponseEntity<String> completeSaleCar(
            @RequestParam(name = "carId") String carId
    ) {
        try {
            marketplaceCarService.completeSaleCar(carId);
            return ResponseEntity.ok("최종 판매 완료!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("서버 오류가 발생했습니다. 나중에 다시 시도해주세요.");
        }
    }

    @Operation(summary = "이차어때 추천", description = "이차어때의 적정 / 저렴 / 비싼 차량을 추천합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "이차어때 추천 성공.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MarketplaceCarRecommandListResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 데이터 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/car/recommend")
    public ResponseEntity<?> getCarRecommendation(
            @RequestBody MarketplaceCarRecommandRequest request
    ) {
        MarketplaceCarRecommandListResponse response = marketplaceCarService.getRecommand(request);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/cars/search/base")
    public ResponseEntity<BaseCarResponse> getBaseCar(
            @RequestParam String licensePlate,
            @RequestParam String ownerName) {
        BaseCar baseCar = marketplaceCarService.findBaseCar(licensePlate, ownerName);

        BaseCarResponse response = BaseCarResponse.builder()
                .id(baseCar.getId())
                .ownerName(baseCar.getOwnerName())
                .carDetails(baseCar.getCarDetails())
                .mainImage(baseCar.getMainImage())
                .build();

        return ResponseEntity.ok(response);
    }


    @GetMapping("/cars/{carId}/detail")
    public ResponseEntity<MarketplaceCarDetailPageResponse> detailCar(
            @PathVariable String carId) {

        try {
            // Service 호출을 통해 차량 상세 정보를 가져옴
            MarketplaceCarDetailPageResponse response = marketplaceCarService.getCarDetailPageResponse(carId);

            // 성공적으로 정보를 가져왔을 때 HTTP 200 OK와 함께 응답
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            // 차량이 없거나 잘못된 요청에 대해 HTTP 404 Not Found와 에러 메시지 응답
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        } catch (Exception e) {
            // 기타 예외 발생 시 HTTP 500 Internal Server Error 응답
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/cars/top-liked")
    public ResponseEntity<List<MarketplaceCarResponse>> getTopLikedCars(){
        List<MarketplaceCarResponse> topCars = marketplaceCarService.getTop5CarsByLikes();
        return ResponseEntity.ok(topCars);
    }

    @DeleteMapping("/cars/{carId}")
    public ResponseEntity<String> deleteCar(@PathVariable String carId) {
            try {
                marketplaceCarService.deleteCar(carId);
                return ResponseEntity.ok("차량이 성공적으로 삭제되었습니다. ID: " + carId);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("차량 삭제 중 문제가 발생했습니다.");
            }
        }
    }
