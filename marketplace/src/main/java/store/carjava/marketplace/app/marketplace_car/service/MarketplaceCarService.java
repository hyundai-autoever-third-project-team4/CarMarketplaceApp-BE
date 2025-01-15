package store.carjava.marketplace.app.marketplace_car.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import store.carjava.marketplace.app.base_car.entity.BaseCar;
import store.carjava.marketplace.app.base_car.repository.BaseCarRepository;
import store.carjava.marketplace.app.car_purchase_history.dto.CarPurchaseHistoryInfoDto;
import store.carjava.marketplace.app.car_sales_history.dto.CarSalesHistoryInfoDto;
import store.carjava.marketplace.app.car_sales_history.entity.CarSalesHistory;
import store.carjava.marketplace.app.car_sales_history.repository.CarSalseHistoryRepository;
import store.carjava.marketplace.app.like.dto.LikeInfoDto;
import store.carjava.marketplace.app.marketplace_car.dto.MarketplaceCarDetailsDto;
import store.carjava.marketplace.app.marketplace_car.dto.MarketplaceCarRegisterRequest;
import store.carjava.marketplace.app.marketplace_car.dto.MarketplaceCarResponse;
import store.carjava.marketplace.app.marketplace_car.dto.MarketplaceCarSendToManagerDto;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;
import store.carjava.marketplace.app.marketplace_car.exception.*;
import store.carjava.marketplace.app.marketplace_car.repository.MarketplaceCarRepository;
import store.carjava.marketplace.app.marketplace_car_extra_option.dto.MarketplaceCarExtraOptionInfoDto;
import store.carjava.marketplace.app.marketplace_car_image.dto.MarketplaceCarImageInfoDto;
import store.carjava.marketplace.app.marketplace_car_option.dto.marketplaceCarOptionInfoDto;
import store.carjava.marketplace.app.reservation.dto.ReservationInfoDto;
import store.carjava.marketplace.app.test_drive_center.dto.TestDriveCenterChangeDto;
import store.carjava.marketplace.app.test_drive_center.entity.TestDriveCenter;
import store.carjava.marketplace.app.test_drive_center.repository.TestDriveCenterRepository;
import store.carjava.marketplace.app.user.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MarketplaceCarService {

    private final MarketplaceCarRepository marketplaceCarRepository;
    private final BaseCarRepository baseCarRepository;
    private final TestDriveCenterRepository testDriveCenterRepository;
    private final CarSalseHistoryRepository carSalseHistoryRepository;

    public List<MarketplaceCarResponse> getFilteredCars(List<String> models, List<String> fuelTypes, String brand, List<String> colorTypes,
                                                        String driveType, String licensePlate, String transmission,
                                                        List<String> vehicleTypes, Integer modelYear, Integer seatingCapacity,
                                                        Long maxPrice, Long minPrice, Integer minMileage,
                                                        Integer maxMileage, Integer minModelYear, Integer maxModelYear,
                                                        List<Long> optionIds, String testDriveCenterName, String status,
                                                        Integer minEngineCapacity, Integer maxEngineCapacity, String name,
                                                        String sortOrder, Pageable pageable
    ) {

        // 비즈니스 로직 검증
        if (minPrice != null && maxPrice != null &&
                minPrice > maxPrice) {
            throw new MinPriceExceedsMaxPriceException();
        }

        if (minModelYear != null && maxModelYear != null &&
                minModelYear > maxModelYear) {
            throw new MaxPriceLessThanMinPriceException();
        }

        // 브랜드가 주어졌을 때, 해당 브랜드가 있는지 확인
        if (brand != null && !brandExists(brand)) {
            throw new BrandNotFoundException(brand);  // 존재하지 않으면 예외 발생
        }
//        // 유효한 연료타입인지 확인
//        if (fuelTypes != null && !fuelTypes.isEmpty()) {
//            for (String fuelType : fuelTypes) {
//                if (!isValidFuelType(fuelType)) {
//                    throw new FuelTypeNotFoundException(fuelType);
//                }
//            }
//        }

        //유효한 상태인지 확인
        if(status != null && !isValidStatus(status)) {
            throw new StatusNotFoundException(status);
        }

        var filteredCars = marketplaceCarRepository.filterCars(models, fuelTypes, brand, colorTypes,
                driveType, licensePlate, transmission, vehicleTypes,
                modelYear, seatingCapacity, maxPrice, minPrice, minMileage,
                maxMileage, minModelYear, maxModelYear, optionIds, testDriveCenterName,
                status, minEngineCapacity, maxEngineCapacity, name, sortOrder, pageable);

        if (filteredCars.isEmpty()) {
            throw new CarNotFoundException(); // 예외 발생
        }

        return filteredCars.stream()
                .map(this::buildMarketplaceCarResponse)
                .toList();
    }


    // 유효한 연료타입을 확인하는 메서드
    private boolean isValidFuelType(String fuelType) {
        List<String> validFuelTypes = List.of("가솔린", "디젤", "전기", "하이브리드");
        return validFuelTypes.contains(fuelType);
    }

    // 유효한 연료타입을 확인하는 메서드
    private boolean isValidStatus(String status) {
        List<String> validStatus = List.of("구매 가능", "승인 대기", "구매 불가");
        return validStatus.contains(status);
    }


    // 브랜드가 존재하는지 체크하는 메서드
    private boolean brandExists(String brand) {
        // 예시로 단순히 `true` 또는 `false` 반환하도록 할 수 있습니다.
        List<String> validBrands = List.of("현대", "제네시스");  // 실제 브랜드 목록
        return validBrands.contains(brand);
    }

    // 모든 차량 데이터 조회
    public List<MarketplaceCarResponse> getAllCars() {
        return marketplaceCarRepository.findAll()
                .stream()
                .map(this::buildMarketplaceCarResponse)
                .toList();
    }


    private MarketplaceCarResponse buildMarketplaceCarResponse(MarketplaceCar car) {
        // CarDetailsDto 생성
        MarketplaceCarDetailsDto carDetailsDto = MarketplaceCarDetailsDto.builder()
                .brand(car.getCarDetails().getBrand())
                .model(car.getCarDetails().getModel())
                .driveType(car.getCarDetails().getDriveType())
                .engineCapacity(car.getCarDetails().getEngineCapacity())
                .exteriorColor(car.getCarDetails().getExteriorColor())
                .interiorColor(car.getCarDetails().getInteriorColor())
                .colorType(car.getCarDetails().getColorType())
                .fuelType(car.getCarDetails().getFuelType())
                .licensePlate(car.getCarDetails().getLicensePlate())
                .mileage(car.getCarDetails().getMileage())
                .modelYear(car.getCarDetails().getModelYear())
                .name(car.getCarDetails().getName())
                .seatingCapacity(car.getCarDetails().getSeatingCapacity())
                .transmission(car.getCarDetails().getTransmission())
                .vehicleType(car.getCarDetails().getVehicleType())
                .registrationDate(car.getCarDetails().getRegistrationDate())
                .build();

        // ReservationDto 리스트 생성
        List<ReservationInfoDto> reservationDtos = car.getReservations().stream()
                .map(reservation -> ReservationInfoDto.builder()
                        .id(reservation.getId())
                        .marketplaceCarId(car.getId())  // MarketplaceCar의 ID 포함
                        .userId(reservation.getUser().getId())  // User의 ID 포함
                        .reservationDate(reservation.getReservationDate())
                        .reservationTime(reservation.getReservationTime())
                        .createdAt(reservation.getCreatedAt())
                        .build())
                .collect(Collectors.toList());

        // LikeDto 리스트 생성
        List<LikeInfoDto> likeDtos = car.getLikes().stream()
                .map(like -> LikeInfoDto.builder()
                        .id(like.getId())
                        .marketplaceCarId(car.getId())
                        .userId(like.getUser().getId())
                        .build())
                .collect(Collectors.toList());

        // CarMarketplaceCarImageDto 리스트 생성
        List<MarketplaceCarImageInfoDto> carMarketplaceCarImageDtos = car.getMarketplaceCarImages().stream()
                .map(marketplaceCarImage -> MarketplaceCarImageInfoDto.builder()
                        .id(marketplaceCarImage.getId())
                        .marketplaceCarId(car.getId())
                        .imageUrl(marketplaceCarImage.getImageUrl())
                        .build())
                .collect(Collectors.toList());

        // CarMarketplaceCarExtraOptionDto 리스트 생성
        List<MarketplaceCarExtraOptionInfoDto> carMarketplaceCarExtraOptionDtos = car.getMarketplaceCarExtraOptions().stream()
                .map(extraOption -> MarketplaceCarExtraOptionInfoDto.builder()
                        .id(extraOption.getId())                   // ExtraOption의 ID
                        .marketplaceCarId(car.getId())             // MarketplaceCar의 ID
                        .name(extraOption.getName())               // 옵션 이름
                        .price(extraOption.getPrice())             // 옵션 가격
                        .build())
                .collect(Collectors.toList());

        // CarPurchaseHistoryInfoDto 리스트 생성
        List<CarPurchaseHistoryInfoDto>  carPurchaseHistoryInfoDtos =car.getCarPurchaseHistories().stream()
                .map(purchaseHistory -> CarPurchaseHistoryInfoDto.builder()
                        .id(purchaseHistory.getId())
                        .marketplaceCarId(car.getId())
                        .userId(purchaseHistory.getUser().getId())
                        .build())
                .collect(Collectors.toList());

        // CarPurchaseHistoryInfoDto 리스트 생성
        List<CarSalesHistoryInfoDto>  carSalesHistoryInfoDtos =car.getCarSalesHistories().stream()
                .map(salesHistory -> CarSalesHistoryInfoDto.builder()
                        .id(salesHistory.getId())
                        .marketplaceCarId(car.getId())
                        .userId(salesHistory.getUser().getId())
                        .build())
                .collect(Collectors.toList());

        // marketplaceCarOptionInfoDto 리스트 생성
        List<marketplaceCarOptionInfoDto> marketplaceCarOptionInfoDtos = car.getMarketplaceCarOptions().stream()
                .map(marketplaceCarOption -> marketplaceCarOptionInfoDto.builder()
                        .id(marketplaceCarOption.getId())
                        .marketplaceCarId(marketplaceCarOption.getMarketplaceCar().getId())
                        .optionId(marketplaceCarOption.getOption().getId())
                        .optionName(marketplaceCarOption.getOption().getName())
                        .optionDescription(marketplaceCarOption.getOption().getDescription())
                        .optionImg(marketplaceCarOption.getOption().getImage())
                        .isPresent(marketplaceCarOption.getIsPresent())
                        .build())
                .collect(Collectors.toList());

        // MarketplaceCarResponse 생성
        return MarketplaceCarResponse.builder()
                .id(car.getId())
                .carDetails(carDetailsDto)
                .testDriveCenterName(car.getTestDriveCenter() != null ? car.getTestDriveCenter().getName() : null)
                .price(car.getPrice())
                .marketplaceRegistrationDate(car.getMarketplaceRegistrationDate())
                .status(car.getStatus())
                .mainImage(car.getMainImage())
                .reservationDtos(reservationDtos) // 예약 리스트 포함
                .likeDtos(likeDtos)
                .marketplaceCarImageDtos(carMarketplaceCarImageDtos)
                .carMarketplaceCarExtraOptionDtos(carMarketplaceCarExtraOptionDtos)
                .carPurchaseHistoryInfoDtos(carPurchaseHistoryInfoDtos)
                .carSalesHistoryInfoDtos(carSalesHistoryInfoDtos)
                .marketplaceCarOptionInfoDtos(marketplaceCarOptionInfoDtos)
                .build();
    }


    // 처음 판매자가 판매차량을 등록할때 등록하는 service
    public void sellRegisterCar(MarketplaceCarRegisterRequest request) {
        // BaseCar 조회
        BaseCar baseCar = baseCarRepository.findByCarDetails_LicensePlateAndOwnerName(
                        request.licensePlate(),
                        request.ownerName())
                .orElseThrow(() -> new IllegalArgumentException("해당 번호판과 이름에 해당하는 차량이 존재하지 않습니다."));

        // MarketplaceCar 생성
        MarketplaceCar marketplaceCar = MarketplaceCar.builder()
                .id(baseCar.getId())
                .carDetails(baseCar.getCarDetails()) // BaseCar에서 CarDetails 설정
                .price(0L) // 요청에서 가격 설정
                .status("판매 대기") // 상태
                .mainImage(baseCar.getMainImage())
                .marketplaceRegistrationDate(LocalDate.now()) // 현재 날짜로 등록일 설정
                .build();

        marketplaceCarRepository.save(marketplaceCar);
    }

    // 상태별 차량 조회 API Service
    public List<MarketplaceCarSendToManagerDto> getCarsByStatus(String status) {

        List<MarketplaceCar> marketplaceCars = marketplaceCarRepository.findByStatus(status);

        List<MarketplaceCarSendToManagerDto> dtoList = new ArrayList<>();
        for (MarketplaceCar marketplaceCar : marketplaceCars) {
            MarketplaceCarSendToManagerDto dto = MarketplaceCarSendToManagerDto.builder()
                    .id(marketplaceCar.getId())
                    .carDetails(
                            MarketplaceCarDetailsDto.builder()
                                    .licensePlate(marketplaceCar.getCarDetails().getLicensePlate())
                                    .brand(marketplaceCar.getCarDetails().getBrand())
                                    .name(marketplaceCar.getCarDetails().getName())
                                    .driveType(marketplaceCar.getCarDetails().getDriveType())
                                    .engineCapacity(marketplaceCar.getCarDetails().getEngineCapacity())
                                    .exteriorColor(marketplaceCar.getCarDetails().getExteriorColor())
                                    .interiorColor(marketplaceCar.getCarDetails().getInteriorColor())
                                    .registrationDate(marketplaceCar.getCarDetails().getRegistrationDate())
                                    .model(marketplaceCar.getCarDetails().getModel())
                                    .colorType(marketplaceCar.getCarDetails().getColorType())
                                    .fuelType(marketplaceCar.getCarDetails().getFuelType())
                                    .mileage(marketplaceCar.getCarDetails().getMileage())
                                    .modelYear(marketplaceCar.getCarDetails().getModelYear())
                                    .seatingCapacity(marketplaceCar.getCarDetails().getSeatingCapacity())
                                    .transmission(marketplaceCar.getCarDetails().getTransmission())
                                    .vehicleType(marketplaceCar.getCarDetails().getVehicleType())
                                    .build()
                    )
                    .testDriveCenterName("") // 기본값으로 설정하거나 동적으로 할당
                    .price(marketplaceCar.getPrice())
                    .marketplaceRegistrationDate(marketplaceCar.getMarketplaceRegistrationDate())
                    .status(marketplaceCar.getStatus())
                    .mainImage(marketplaceCar.getMainImage()) // 메인 이미지 URL
                    .build();
            dtoList.add(dto);
        }
        return dtoList;
    }

    // 관리자가 판매 승인했을 때 가격
    public void approveCar(String id, String testDriverCenterName, Long price) {
        // 차량 조회
        MarketplaceCar car = marketplaceCarRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 차량을 찾을 수 없습니다."));

        // TestDriveCenter 조회
        TestDriveCenter testDriveCenter = testDriveCenterRepository.findByName(testDriverCenterName)
                .orElseThrow(() -> new IllegalArgumentException("해당 이름의 시승 센터를 찾을 수 없습니다."));

        // 차량 상태 업데이트 및 TestDriveCenter 연관 관계 설정
        car = MarketplaceCar.builder()
                .id(car.getId())
                .carDetails(car.getCarDetails())
                .price(price)
                .status("판매 승인")
                .marketplaceRegistrationDate(LocalDate.now())
                .testDriveCenter(testDriveCenter) // 연관된 TestDriveCenter 설정
                .mainImage(car.getMainImage())
                .build();

        // 변경된 차량 저장
        marketplaceCarRepository.save(car);
    }

    public void completeSaleCar(String id) {
        // 차량 조회
        MarketplaceCar car = marketplaceCarRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 차량을 찾을 수 없습니다."));

        // 차량 상태 업데이트
        car = MarketplaceCar.builder()
                .id(car.getId())
                .carDetails(car.getCarDetails())
                .price(car.getPrice())
                .status("구매 가능") // 상태 업데이트
                .marketplaceRegistrationDate(car.getMarketplaceRegistrationDate())
                .mainImage(car.getMainImage())
                .testDriveCenter(car.getTestDriveCenter())
                .build();

        // 변경된 차량 저장
        marketplaceCarRepository.save(car);

        /** user 정보를 받아오면

//        // 현재 로그인된 사용자 정보 가져오기
//        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        // 판매 이력 생성
//        CarSalesHistory salesHstory = CarSalesHistory.builder()
//                .marketplaceCar(car) // 업데이트된 차량 정보
//                .user(currentUser)
//                .build();
//
//        // 판매 이력 저장
//        carSalseHistoryRepository.save(salesHstory);**/
    }








}