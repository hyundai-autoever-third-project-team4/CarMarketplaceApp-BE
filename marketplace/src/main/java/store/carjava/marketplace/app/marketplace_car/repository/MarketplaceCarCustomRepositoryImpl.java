package store.carjava.marketplace.app.marketplace_car.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;
import store.carjava.marketplace.app.marketplace_car.entity.QMarketplaceCar;
import store.carjava.marketplace.app.marketplace_car_option.entity.QMarketplaceCarOption;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class MarketplaceCarCustomRepositoryImpl implements MarketplaceCarCustomRepository {

    private final JPAQueryFactory queryFactory;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<MarketplaceCar> filterCars(String model, String fuelType, String brand, String colorType, String driveType,
                                           String licensePlate, String transmission, String vehicleType, Integer modelYear,
                                           Integer seatingCapacity, Long minPrice, Long maxPrice, Integer minMileage,
                                           Integer maxMileage, Integer minModelYear, Integer maxModelYear, List<Long> optionIds,
                                           String testDriveCenterName, String status, Integer minEngineCapacity, Integer maxEngineCapacity,
                                           String name, String sortOrder, Pageable pageable) {

        QMarketplaceCar marketplaceCar = QMarketplaceCar.marketplaceCar;
        QMarketplaceCarOption marketplaceCarOption = QMarketplaceCarOption.marketplaceCarOption;

        OrderSpecifier<?> orderSpecifier = getOrderSpecifier(sortOrder, marketplaceCar);

        // 메인 쿼리 생성
        var query = queryFactory
                .selectFrom(marketplaceCar)
                .innerJoin(marketplaceCarOption)
                .on(marketplaceCarOption.marketplaceCar.id.eq(marketplaceCar.id))
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
                        allOptionsTrue(marketplaceCarOption, optionIds),
                        testDriveCenterNameEq(marketplaceCar, testDriveCenterName),
                        statusEq(marketplaceCar, status),
                        engineCapacityGreaterOrEqual(marketplaceCar, minEngineCapacity),
                        engineCapacityLessOrEqual(marketplaceCar, maxEngineCapacity),
                        nameContains(marketplaceCar, name)
                )
                .distinct() // 중복 제거
                .orderBy(orderSpecifier);

        // 전체 데이터 개수 조회
        long total = query.fetchCount();

        // 페이징 처리
        List<MarketplaceCar> results = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // Page 객체로 반환
        return new PageImpl<>(results, pageable, total);
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
        return minPrice == null ? null : marketplaceCar.price.goe(minPrice);
    }

    private BooleanExpression priceLessOrEqual(QMarketplaceCar marketplaceCar, Long maxPrice) {
        return maxPrice == null ? null : marketplaceCar.price.loe(maxPrice);
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

        QMarketplaceCar marketplaceCar = QMarketplaceCar.marketplaceCar;

        List<String> validVehicleIds = new JPAQuery<>(entityManager)
                .select(marketplaceCarOption.marketplaceCar.id)
                .from(marketplaceCarOption)
                .innerJoin(marketplaceCarOption.marketplaceCar, marketplaceCar)
                .where(marketplaceCarOption.option.id.in(optionIds))
                .where(marketplaceCarOption.isPresent.isTrue())
                .groupBy(marketplaceCarOption.marketplaceCar.id)
                .having(marketplaceCarOption.option.id.count().eq((long) optionIds.size()))
                .fetch();

        return marketplaceCar.id.in(validVehicleIds);
    }

    private BooleanExpression testDriveCenterNameEq(QMarketplaceCar marketplaceCar, String testDriveCenterName) {
        return testDriveCenterName == null ? null : marketplaceCar.testDriveCenter.name.eq(testDriveCenterName);
    }

    private BooleanExpression statusEq(QMarketplaceCar marketplaceCar, String status) {
        return status == null ? null : marketplaceCar.status.eq(status);
    }

    private BooleanExpression engineCapacityGreaterOrEqual(QMarketplaceCar marketplaceCar, Integer minEngineCapacity) {
        return minEngineCapacity == null ? null : marketplaceCar.carDetails.engineCapacity.loe(minEngineCapacity);
    }

    private BooleanExpression engineCapacityLessOrEqual(QMarketplaceCar marketplaceCar, Integer maxEngineCapacity) {
        return maxEngineCapacity == null ? null : marketplaceCar.carDetails.engineCapacity.goe(maxEngineCapacity);
    }

    private BooleanExpression nameContains(QMarketplaceCar marketplaceCar, String name) {
        return name == null ? null : marketplaceCar.carDetails.name.containsIgnoreCase(name);
    }

    private OrderSpecifier<?> getOrderSpecifier(String sortOrder, QMarketplaceCar marketplaceCar) {
        switch (sortOrder.toUpperCase()) {
            case "최근 연식순":
                return marketplaceCar.carDetails.modelYear.desc();
            case "낮은 가격순":
                return marketplaceCar.price.asc();
            case "높은 가격순":
                return marketplaceCar.price.desc();
            case "짧은 주행거리순":
                return marketplaceCar.carDetails.mileage.asc();
            case "인기순":
            default:
                return marketplaceCar.likes.size().desc();
        }
    }
}
