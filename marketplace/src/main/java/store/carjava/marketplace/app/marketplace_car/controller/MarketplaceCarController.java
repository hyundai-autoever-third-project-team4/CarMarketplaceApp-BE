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
import store.carjava.marketplace.app.marketplace_car.dto.MarketplaceCarRegisterRequest;
import store.carjava.marketplace.app.marketplace_car.dto.MarketplaceCarResponse;
import store.carjava.marketplace.app.marketplace_car.dto.MarketplaceCarSendToManagerDto;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import store.carjava.marketplace.app.marketplace_car.dto.MarketplaceCarRecommandListResponse;
import store.carjava.marketplace.app.marketplace_car.dto.MarketplaceCarRecommandRequest;
import store.carjava.marketplace.app.marketplace_car.dto.MarketplaceCarResponse;

import store.carjava.marketplace.app.marketplace_car.service.MarketplaceCarService;

import java.util.List;

@Slf4j
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


    @Operation(summary = "차량 필터별 조회", description = "필터 조건에 맞는 차량 정보를 조회합니다. 참고 : https://www.notion.so/API-54267de5ae4748d5a42597b21e3f8dd2")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공적으로 차량 정보를 반환했습니다."),
            @ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
    })
    @GetMapping("/filter")
    public ResponseEntity<List<MarketplaceCarResponse>> filterCars(
            @Parameter(description = "차량 모델 : 목록들 : G70, G80, G90, GV70, GV80, 그랜저, 쏘나타, 아반떼, 아이오닉6, 캐스퍼, 팰리세이드")
            @RequestParam(required = false) String model,

            @Parameter(description = "브랜드 : 목록들 : 현대, 제네시스")
            @RequestParam(required = false) String brand,

            @Parameter(description = "색상 : 목록들 : 그레이, 그린, 기타, 레드, 블랙, 블루, 실버, 오렌지, 화이트")
            @RequestParam(required = false) String colorType,

            @Parameter(description = "차량 전륜 2륜 구동 : 목록들 : 2WD, AWD")
            @RequestParam(required = false) String driveType,

            @Parameter(description = "번호판 : 예시 : 183무1416")
            @RequestParam(required = false) String licensePlate,

            @Parameter(description = "변속기 종류 : 목록들 : 자동, 자동 (DCT), 자동 (IVT)")
            @RequestParam(required = false) String transmission,

            @Parameter(description = "차량 종류 : 목록들 : EV, SUV, 승용")
            @RequestParam(required = false) String vehicleType,

            @Parameter(description = "시승소 종류 : 목록들 : 광주 시승소, 대구 시승소, 대전 시승소, 부산 시승소, 서울 강서 시승소, 서울 성북 시승소, 수원 시승소, 울산 시승소, 인천 시승소, 전주 시승소, 제주 시승소, 청주 시승소")
            @RequestParam(required = false) String testDriveCenterName,

            @Parameter(description = "모델 연식 : 목록들 : 2019, 2020, 2021, 2022, 2023, 2024")
            @RequestParam(required = false) Integer modelYear,

            @Parameter(description = "차량 인승 수 : 목록들 : 2, 4, 5, 6, 7, 8")
            @RequestParam(required = false) Integer seatingCapacity,

            @Parameter(description = "연료 타입 : 목록들 : 가솔린, 디젤, 전기, 하이브리드")
            @RequestParam(required = false) String fuelType,

            @Parameter(description = "최대 가격 설정 : 예시 : 20000000")
            @RequestParam(required = false) Long maxPrice,

            @Parameter(description = "최소 가격 설정 : 예시 : 20000000")
            @RequestParam(required = false) Long minPrice,

            @Parameter(description = "최대 주행거리 설정 : 예시 : 80302")
            @RequestParam(required = false) Integer maxMileage,

            @Parameter(description = "최소 주행거리 설정 : 예시 : 80302")
            @RequestParam(required = false) Integer minMileage,

            @Parameter(description = "최소 모델연식 설정 : 예시 : 2019")
            @RequestParam(required = false) Integer minModelYear,

            @Parameter(description = "최대 모델연식 설정 : 예시 : 2024")
            @RequestParam(required = false) Integer maxModelYear,

            @Parameter(description = "옵션 리스트 : 예시 : 1,2,3,6,8,9,13,16")
            @RequestParam(required = false) List<Long> optionIds,

            @Parameter(description = "최대 배기량 설정 : 예시 : 3342")
            @RequestParam(required = false) Integer maxEngineCapacity,

            @Parameter(description = "최소 배기량 설정 : 예시 : 2000")
            @RequestParam(required = false) Integer minEngineCapacity,

            @Parameter(description = "차량 상태 : 목록들 : 구매가능, 승인대기, 구매불가")
            @RequestParam(required = false) String status,

            @Parameter(description = "차량 이름 : 예시 : 2021 G80 가솔린 2.5 터보 AWD 시그니처 디자인 셀렉션Ⅰ")
            @RequestParam(required = false) String name,

            @Parameter(description = "정렬 옵션 : 목록들 : 최근 연식순, 낮은 가격순, 높은 가격순, 짧은 주행거리순, 인기순")
            @RequestParam(defaultValue = "인기순") String sortOrder,

            @Parameter(description = "리스트 조회 페이지네이션")
            @PageableDefault(size = 10, page = 0) Pageable pageable
    ) {
        List<MarketplaceCarResponse> filteredCars = marketplaceCarService.getFilteredCars(
                model, fuelType, brand, colorType,
                driveType, licensePlate,transmission,vehicleType,
                modelYear,seatingCapacity, maxPrice, minPrice, minMileage,
                 maxMileage,  minModelYear,  maxModelYear, optionIds,
                testDriveCenterName, status, maxEngineCapacity, minEngineCapacity,
                name, sortOrder, pageable
        );
        return ResponseEntity.ok(filteredCars);
    }


    @PostMapping("/api-docs/register")
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

    @GetMapping("/api-docs/status")
    public ResponseEntity<List<MarketplaceCarSendToManagerDto>> getCarsByStatus(
            @RequestParam String status) {
        List<MarketplaceCarSendToManagerDto> cars = marketplaceCarService.getCarsByStatus(status);
        return ResponseEntity.ok(cars);
    }

    @PutMapping("/api-docs/approve")
    public ResponseEntity<String> approveCar(
            @RequestParam String carId,
            @RequestParam String testDriveCenterName,
            @RequestParam Long price
        ) {
        try {
            marketplaceCarService.approveCar(carId, testDriveCenterName, price);
            return ResponseEntity.ok("차량 판매가 승인되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("서버 오류가 발생했습니다. 나중에 다시 시도해주세요.");
        }
    }

    @PutMapping("/api-docs/complete-sale")
    public ResponseEntity<String> completeSaleCar(
            @RequestParam String carId
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







    @Operation(summary = "이차어때 추천", description = "이차어때의 적정 / 저렴 / 비싼 차량을 추천합니다. 첫 추천에는 차 id = null. 재추천에는 차 id 필수.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "이차어때 추천 성공. 적정 / 저렴 / 비싼 차량 순. 결과 없는 경우 null 반환",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = MarketplaceCarRecommandListResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 데이터 요청"),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content())
    })
    @PostMapping("/recommend")
    public ResponseEntity<?> getCarRecommandation(
            @RequestBody MarketplaceCarRecommandRequest request
    ) {
        MarketplaceCarRecommandListResponse response = marketplaceCarService.getRecommand(request);

        return ResponseEntity.ok(response);
    }

}