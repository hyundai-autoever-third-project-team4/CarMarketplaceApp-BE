package store.carjava.marketplace.app.marketplace_car.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import store.carjava.marketplace.app.marketplace_car.dto.MarketplaceCarRecommandInfoDto;
import store.carjava.marketplace.app.marketplace_car.dto.MarketplaceCarRecommandListResponse;
import store.carjava.marketplace.app.marketplace_car.dto.MarketplaceCarRecommandRequest;
import store.carjava.marketplace.app.car_purchase_history.dto.CarPurchaseHistoryInfoDto;
import store.carjava.marketplace.app.car_sales_history.dto.CarSalesHistoryInfoDto;
import store.carjava.marketplace.app.like.dto.LikeInfoDto;
import store.carjava.marketplace.app.marketplace_car.dto.MarketplaceCarDetailsDto;
import store.carjava.marketplace.app.marketplace_car.dto.MarketplaceCarResponse;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;
import store.carjava.marketplace.app.marketplace_car.exception.*;
import store.carjava.marketplace.app.marketplace_car.repository.MarketplaceCarRepository;
import store.carjava.marketplace.app.marketplace_car_extra_option.dto.MarketplaceCarExtraOptionInfoDto;
import store.carjava.marketplace.app.marketplace_car_image.dto.MarketplaceCarImageInfoDto;
import store.carjava.marketplace.app.marketplace_car_option.dto.marketplaceCarOptionInfoDto;
import store.carjava.marketplace.app.reservation.dto.ReservationInfoDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MarketplaceCarService {

    private final MarketplaceCarRepository marketplaceCarRepository;


    public List<MarketplaceCarResponse> getFilteredCars(String model, String fuelType, String brand, String colorType,
                                                        String driveType, String licensePlate, String transmission,
                                                        String vehicleType, Integer modelYear, Integer seatingCapacity,
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
        // 유효한 연료타입인지 확인
        if (fuelType != null && !isValidFuelType(fuelType)) {
            throw new FuelTypeNotFoundException(fuelType);  // 유효하지 않으면 예외 발생
        }
        //유효한 상태인지 확인
        if(status != null && !isValidStatus(status)) {
            System.out.println("#####################"+status);
            throw new StatusNotFoundException(status);
        }

        var filteredCars = marketplaceCarRepository.filterCars(model, fuelType, brand, colorType,
                driveType, licensePlate, transmission, vehicleType,
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

    public MarketplaceCarRecommandListResponse getRecommand(MarketplaceCarRecommandRequest request) {
        long budget = request.budget() * 10000;
        String vehicle = request.vehicleType();

        if(!vehicle.equals("승용") && !vehicle.equals("SUV") && !vehicle.equals("승합") && !vehicle.equals("EV")){
            throw new VehicleTypeNotFoundException();
        }

        MarketplaceCar normal, less, over;

        // 적정, 저렴 추천
        List<MarketplaceCar> carExist = marketplaceCarRepository.findTop2ByCarDetails_VehicleTypeAndPriceLessThanEqualOrderByPriceDesc(vehicle, budget);
        if (carExist.isEmpty()) {
            // 예산 내 차가 0대인 경우 - 너무 적은 예산
            normal = null;
            less = null;
        } else if (carExist.size() == 1) {
            // 예산 내 차가 1대인 경우
            normal = carExist.get(0);
            less = null;
        } else{
            // 예산 내 차가 여러 대인 경우
            long budgetLow = (long) (budget * 0.92);
            long budgetHigh = (long) (budget * 0.97);

            List<MarketplaceCar> carList = marketplaceCarRepository.findMarketplaceCarProperList(budgetLow, budgetHigh, vehicle);

            // 적정 가격 범위 내에 차가 없거나 1개만 존재하는 경우
            if (carList.size() < 2) {
                long normalPrice;

                // 범위 내 차가 없음
                if (carList.isEmpty()) {
                    normal = carExist.get(0);
                    normalPrice = carExist.get(0).getPrice();
                } else {    // 범위 내 차가 1대
                    normal = carList.get(0);
                    normalPrice = carList.get(0).getPrice();
                }
                Optional<MarketplaceCar> lessCarExist = marketplaceCarRepository.findTopByCarDetails_VehicleTypeAndPriceLessThanEqualOrderByPriceDesc(vehicle, normalPrice - 1);
                less = lessCarExist.orElse(null);
            }
            else{
                normal = carList.get(0);
                less = carList.get(carList.size() - 1);
            }
        }

        // 초과 추천
        Optional<MarketplaceCar> overExist = marketplaceCarRepository.findTopByPriceGreaterThanOrderByPrice(budget);
        if (overExist.isEmpty()) {
            over = null;
        }
        else if (normal == null) {
            over = overExist.get();
        }
        else{
            // 고급 모델 차량
            MarketplaceCar upgrade1 = marketplaceCarRepository.findUpgradeModelCarOverPrice(budget, vehicle, normal);
            // 동일 모델 차량, 짧은 주행거리 + 최신 연식
            MarketplaceCar upgrade2 = marketplaceCarRepository.findCarMoreOptionOverPrice(budget, vehicle, normal);

            if (upgrade1 != null && upgrade2 != null) {
                if (upgrade1.getPrice() < upgrade2.getPrice()) { over = upgrade1; }
                else { over = upgrade2; }
            }
            else if (upgrade1 != null) { over = upgrade1; }
            else if (upgrade2 != null) { over = upgrade2; }
            else { over = overExist.get(); }
        }

        return MarketplaceCarRecommandListResponse.of(
                MarketplaceCarRecommandInfoDto.of(normal),
                MarketplaceCarRecommandInfoDto.of(less),
                MarketplaceCarRecommandInfoDto.of(over));
    }
}