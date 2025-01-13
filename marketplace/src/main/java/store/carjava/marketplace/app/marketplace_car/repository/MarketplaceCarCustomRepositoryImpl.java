package store.carjava.marketplace.app.marketplace_car.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;
import store.carjava.marketplace.app.marketplace_car.entity.QMarketplaceCar;
import store.carjava.marketplace.app.marketplace_car_option.entity.QMarketplaceCarOption;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class MarketplaceCarCustomRepositoryImpl implements MarketplaceCarCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MarketplaceCar> filterCars(String model, String fuelType, String brand, String colorType, String driveType,
                                           String licensePlate, String transmission, String vehicleType, Integer modelYear,
                                           Integer seatingCapacity, Long minPrice, Long maxPrice, Integer minMileage,
                                           Integer maxMileage, Integer minModelYear, Integer maxModelYear, List<Long> optionIds,
                                           String testDriveCenterName, String status, Integer minEngineCapacity, Integer maxEngineCapacity,
                                           String name, String sortOrder
    ) {
        QMarketplaceCar marketplaceCar = QMarketplaceCar.marketplaceCar;
        QMarketplaceCarOption marketplaceCarOption = QMarketplaceCarOption.marketplaceCarOption;

        OrderSpecifier<?> orderSpecifier = getOrderSpecifier(sortOrder, marketplaceCar);

        return queryFactory
                .selectFrom(marketplaceCar)
                .innerJoin(marketplaceCarOption)
                .on(marketplaceCarOption.marketplaceCar.id.eq(marketplaceCar.id)) // marketplace_car와 marketplace_car_option 연결
                .where(
                        modelEq(marketplaceCar, model),
                        fuelTypeEq(marketplaceCar, fuelType),
                        brandEq(marketplaceCar, brand),
                        colorTypeEq(marketplaceCar, colorType),
                        driveTypeEq(marketplaceCar, driveType),
                        licensePlateEq(marketplaceCar, licensePlate),
                        transmissionEq(marketplaceCar, transmission),
                        vehicleTypeEq(marketplaceCar, vehicleType),
                        modelYearEq(marketplaceCar, modelYear),
                        seatingCapacityEq(marketplaceCar, seatingCapacity),
                        priceGreaterOrEqual(marketplaceCar, minPrice),
                        priceLessOrEqual(marketplaceCar, maxPrice),
                        mileageGreaterOrEqual(marketplaceCar, minMileage),
                        mileageLessOrEqual(marketplaceCar, maxMileage),
                        modelYearGreaterOrEqual(marketplaceCar, minModelYear),
                        modelYearLessOrEqual(marketplaceCar, maxModelYear),
                        allOptionsTrue(marketplaceCarOption, optionIds),// 옵션 필터링 조건 추가
                        testDriveCenterNameEq(marketplaceCar, testDriveCenterName),
                        statusEq(marketplaceCar, status),
                        engineCapacityGreaterOrEqual(marketplaceCar, minEngineCapacity),
                        engineCapacityLessOrEqual(marketplaceCar, maxEngineCapacity),
                        nameContains(marketplaceCar, name)
                )
                .distinct() // 중복 제거
                .orderBy(orderSpecifier) // 정렬 조건 추가
                .fetch();
    }

    private BooleanExpression modelEq(QMarketplaceCar marketplaceCar, String model) {
        return model == null ? null : marketplaceCar.carDetails.model.eq(model);
    }

    private BooleanExpression fuelTypeEq(QMarketplaceCar marketplaceCar, String fuelType) {
        return fuelType == null ? null : marketplaceCar.carDetails.fuelType.eq(fuelType);
    }

    private BooleanExpression brandEq(QMarketplaceCar marketplaceCar, String brand) {
        return brand == null ? null : marketplaceCar.carDetails.brand.eq(brand);
    }

    private BooleanExpression colorTypeEq(QMarketplaceCar marketplaceCar, String colorType) {
        return colorType == null ? null : marketplaceCar.carDetails.colorType.eq(colorType);
    }
    private BooleanExpression driveTypeEq(QMarketplaceCar marketplaceCar, String driveType) {
        return driveType == null ? null : marketplaceCar.carDetails.driveType.eq(driveType);
    }
    private BooleanExpression licensePlateEq(QMarketplaceCar marketplaceCar, String licensePlate) {
        return licensePlate == null ? null : marketplaceCar.carDetails.licensePlate.eq(licensePlate);
    }
    private BooleanExpression transmissionEq(QMarketplaceCar marketplaceCar, String transmission) {
        return transmission == null ? null : marketplaceCar.carDetails.transmission.eq(transmission);
    }
    private BooleanExpression vehicleTypeEq(QMarketplaceCar marketplaceCar, String vehicleType) {
        return vehicleType == null ? null : marketplaceCar.carDetails.vehicleType.eq(vehicleType);
    }


    private BooleanExpression modelYearEq(QMarketplaceCar marketplaceCar, Integer modelYear) {
        return modelYear == null ? null : marketplaceCar.carDetails.modelYear.eq(modelYear);
    }
    private BooleanExpression seatingCapacityEq(QMarketplaceCar marketplaceCar, Integer seatingCapacity) {
        return seatingCapacity == null ? null : marketplaceCar.carDetails.seatingCapacity.eq(seatingCapacity);
    }
    private BooleanExpression priceGreaterOrEqual(QMarketplaceCar marketplaceCar, Long minPrice) {
        return minPrice == null ? null : marketplaceCar.price.goe(minPrice); // goe: greater or equal
    }

    private BooleanExpression priceLessOrEqual(QMarketplaceCar marketplaceCar, Long maxPrice) {
        return maxPrice == null ? null : marketplaceCar.price.loe(maxPrice); // loe: less or equal
    }

    private BooleanExpression mileageGreaterOrEqual(QMarketplaceCar marketplaceCar, Integer minMileage) {
        return minMileage == null ? null : marketplaceCar.carDetails.mileage.goe(minMileage);
    }

    private BooleanExpression mileageLessOrEqual(QMarketplaceCar marketplaceCar, Integer maxMileage) {
        return maxMileage == null ? null : marketplaceCar.carDetails.mileage.loe(maxMileage);
    }

    private BooleanExpression modelYearGreaterOrEqual(QMarketplaceCar marketplaceCar, Integer minModelYear) {
        return minModelYear == null ? null : marketplaceCar.carDetails.modelYear.goe(minModelYear);
    }

    private BooleanExpression modelYearLessOrEqual(QMarketplaceCar marketplaceCar, Integer maxModelYear) {
        return maxModelYear == null ? null : marketplaceCar.carDetails.modelYear.loe(maxModelYear);
    }

    private BooleanExpression allOptionsTrue(QMarketplaceCarOption marketplaceCarOption, List<Long> optionIds) {
        if (optionIds == null || optionIds.isEmpty()) {
            return null;
        }

        for(int i=0;i<optionIds.size();i++) {
            System.out.println("############# : " + optionIds.get(i));
        }

        return JPAExpressions
                .selectOne()
                .from(marketplaceCarOption)
                .where(
                        marketplaceCarOption.marketplaceCar.id.eq(marketplaceCarOption.marketplaceCar.id),
                        marketplaceCarOption.option.id.in(optionIds),
                        marketplaceCarOption.isPresent.isTrue()
                )
                .exists(); // exists()로 변경
    }


    private BooleanExpression testDriveCenterNameEq(QMarketplaceCar marketplaceCar, String testDriveCenterName) {
        return testDriveCenterName == null ? null : marketplaceCar.testDriveCenter.name.eq(testDriveCenterName);
    }

    private BooleanExpression statusEq(QMarketplaceCar marketplaceCar, String status) {
        return status == null ? null : marketplaceCar.status.eq(status);
    }

    private BooleanExpression engineCapacityGreaterOrEqual(QMarketplaceCar marketplaceCar, Integer minEngineCapacity) {
        return minEngineCapacity == null ? null : marketplaceCar.carDetails.engineCapacity.loe(minEngineCapacity); // loe: less or equal
    }

    private BooleanExpression engineCapacityLessOrEqual(QMarketplaceCar marketplaceCar, Integer maxEngineCapacity) {
        return maxEngineCapacity == null ? null : marketplaceCar.carDetails.engineCapacity.goe(maxEngineCapacity); // goe: greater or equal
    }
    private BooleanExpression nameContains(QMarketplaceCar marketplaceCar, String name) {
        return name == null ? null : marketplaceCar.carDetails.name.containsIgnoreCase(name); // 부분 검색
    }

    private OrderSpecifier<?> getOrderSpecifier(String sortOrder, QMarketplaceCar marketplaceCar) {
        switch (sortOrder.toUpperCase()) {
            case "최근 연식순": // 최근 연식순
                return marketplaceCar.carDetails.modelYear.desc();
            case "낮은 가격순": // 낮은 가격순
                return marketplaceCar.price.asc();
            case "높은 가격순": // 높은 가격순
                return marketplaceCar.price.desc();
            case "짧은 주행거리순": // 짧은 주행거리순
                return marketplaceCar.carDetails.mileage.asc();
            case "인기순": // 인기순 (default)
            default:
                return marketplaceCar.likes.size().desc();
        }
    }


}