package store.carjava.marketplace.app.marketplace_car.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.carjava.marketplace.app.car_purchase_history.dto.CarPurchaseHistoryInfoDto;
import store.carjava.marketplace.app.car_sales_history.dto.CarSalesHistoryInfoDto;
import store.carjava.marketplace.app.like.dto.LikeInfoDto;
import store.carjava.marketplace.app.marketplace_car.dto.MarketplaceCarDetailsDto;
import store.carjava.marketplace.app.marketplace_car.dto.MarketplaceCarResponse;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;
import store.carjava.marketplace.app.marketplace_car.repository.MarketplaceCarRepository;
import store.carjava.marketplace.app.marketplace_car_extra_option.dto.MarketplaceCarExtraOptionInfoDto;
import store.carjava.marketplace.app.marketplace_car_image.dto.MarketplaceCarImageInfoDto;
import store.carjava.marketplace.app.marketplace_car_option.dto.marketplaceCarOptionInfoDto;
import store.carjava.marketplace.app.reservation.dto.ReservationInfoDto;

import java.util.List;
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
                                                        String sortOrder
    ) {

        return marketplaceCarRepository.filterCars(model, fuelType, brand, colorType,
                        driveType, licensePlate, transmission, vehicleType,
                        modelYear, seatingCapacity, maxPrice, minPrice,minMileage,
                        maxMileage, minModelYear, maxModelYear ,optionIds, testDriveCenterName,
                        status, minEngineCapacity, maxEngineCapacity, name, sortOrder
                )

                .stream()
                .map(this::buildMarketplaceCarResponse)
                .toList();
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
}