package store.carjava.marketplace.app.marketplace_car.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
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
import store.carjava.marketplace.app.car_model_grade.entity.QCarModelGrade;
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
    public Page<MarketplaceCar> filterCars(List<String> models, List<String> fuelTypes, String brand, List<String> colorTypes, String driveType,
                                           String licensePlate, String transmission, List<String> vehicleTypes, Integer modelYear,
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
                .leftJoin(marketplaceCarOption)
                .on(marketplaceCarOption.marketplaceCar.id.eq(marketplaceCar.id))
                .where(
                        modelsIn(marketplaceCar, models),
                        fuelTypeIn(marketplaceCar, fuelTypes),
                        brandEq(marketplaceCar, brand),
                        colorTypeIn(marketplaceCar, colorTypes),
                        driveTypeEq(marketplaceCar, driveType),
                        licensePlateEq(marketplaceCar, licensePlate),
                        transmissionEq(marketplaceCar, transmission),
                        vehicleTypeIn(marketplaceCar, vehicleTypes),
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
        List<MarketplaceCar> results =  query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // Page 객체로 반환
        return new PageImpl<>(results, pageable, total);
    }

    private BooleanExpression modelsIn(QMarketplaceCar marketplaceCar, List<String> models) {
        return (models == null || models.isEmpty()) ? null : marketplaceCar.carDetails.model.in(models);
    }

    private BooleanExpression fuelTypeIn(QMarketplaceCar marketplaceCar, List<String> fuelTypes) {
        return (fuelTypes == null || fuelTypes.isEmpty()) ? null : marketplaceCar.carDetails.fuelType.in(fuelTypes);
    }

    private BooleanExpression brandEq(QMarketplaceCar marketplaceCar, String brand) {
        return brand == null ? null : marketplaceCar.carDetails.brand.eq(brand);
    }

    private BooleanExpression colorTypeIn(QMarketplaceCar marketplaceCar, List<String> colorTypes) {
        return (colorTypes == null || colorTypes.isEmpty()) ? null : marketplaceCar.carDetails.colorType.in(colorTypes);

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

    private BooleanExpression vehicleTypeIn(QMarketplaceCar marketplaceCar, List<String> vehicleTypes) {
        return (vehicleTypes == null || vehicleTypes.isEmpty()) ? null : marketplaceCar.carDetails.vehicleType.in(vehicleTypes);
    }

    private BooleanExpression modelYearEq(QMarketplaceCar marketplaceCar, Integer modelYear) {
        return modelYear == null ? null : marketplaceCar.carDetails.modelYear.eq(modelYear);
    }

    private BooleanExpression seatingCapacityEq(QMarketplaceCar marketplaceCar, Integer seatingCapacity) {
        return seatingCapacity == null ? null : marketplaceCar.carDetails.seatingCapacity.eq(seatingCapacity);
    }

    private BooleanExpression priceGreaterOrEqual(QMarketplaceCar marketplaceCar, Long minPrice) {
        return minPrice == null ? null : marketplaceCar.price.loe(minPrice);
    }

    private BooleanExpression priceLessOrEqual(QMarketplaceCar marketplaceCar, Long maxPrice) {
        return maxPrice == null ? null : marketplaceCar.price.goe(maxPrice);
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

    // 이차어때 차량 추천
    // 예산보다 조금 적은 금액 내 차량 리스트
    @Override
    public List<MarketplaceCar> findMarketplaceCarProperList(long budgetLow, long budgetHigh, List<String> vehicle) {
        QMarketplaceCar marketplaceCar = QMarketplaceCar.marketplaceCar;
        QCarModelGrade carModelGrade = QCarModelGrade.carModelGrade;

        // 예산 범위 내 가장 많은 차종 구하기
        String carmodel = queryFactory.select(marketplaceCar.carDetails.model)
                .from(marketplaceCar)
                .leftJoin(carModelGrade).on(marketplaceCar.carDetails.model.eq(carModelGrade.model))
                .where(
                        marketplaceCar.price.between(budgetLow, budgetHigh)
                                .and(marketplaceCar.carDetails.vehicleType.in(vehicle))
                )
                .groupBy(marketplaceCar.carDetails.model, carModelGrade.grade)
                .orderBy(
                        marketplaceCar.carDetails.model.count().desc(),
                        carModelGrade.grade.desc()
                )
                .limit(1)
                .fetchOne();

        if(carmodel == null) carmodel = "";

        return queryFactory
                .selectFrom(marketplaceCar)
                .where(marketplaceCar.carDetails.model.eq(carmodel)
                        .and(marketplaceCar.price.between(budgetLow, budgetHigh)))
                .orderBy(marketplaceCar.price.desc())
                .fetch();
    }

    // 초과 금액 추천 - 고급 모델 중 추천
    @Override
    public MarketplaceCar findUpgradeModelCarOverPrice(long budget, List<String> vehicle, MarketplaceCar car) {
        QMarketplaceCar marketplaceCar = QMarketplaceCar.marketplaceCar;
        QCarModelGrade carModelGrade = QCarModelGrade.carModelGrade;

        Integer carGrade = queryFactory.select(carModelGrade.grade).from(carModelGrade)
                .where(carModelGrade.model.eq(car.getCarDetails().getModel()))
                .fetchOne();

        // 적정 추천보다 고급 모델이면서 주행거리가 비슷한 차량
        return queryFactory.selectFrom(marketplaceCar)
                .leftJoin(carModelGrade)
                .on(marketplaceCar.carDetails.model.eq(carModelGrade.model))
                .where(marketplaceCar.carDetails.vehicleType.in(vehicle)
                        .and(marketplaceCar.price.gt(budget))
                        .and(carModelGrade.model.in(
                                JPAExpressions.select(carModelGrade.model)
                                        .from(carModelGrade)
                                        .where(carModelGrade.vehicleType.in(vehicle)
                                                .and(carModelGrade.grade.gt(carGrade))
                                        )
                        ))
                )
                .orderBy(marketplaceCar.price.asc())
                .limit(1)
                .fetchOne();
    }

    // 초과 금액 추천 - 동일 모델 중 추천
    @Override
    public MarketplaceCar findCarMoreOptionOverPrice(long budget, MarketplaceCar car) {
        QMarketplaceCar marketplaceCar = QMarketplaceCar.marketplaceCar;
        //QMarketplaceCarOption carOption = QMarketplaceCarOption.marketplaceCarOption;
        //QMarketplaceCarExtraOption carExtraOption = QMarketplaceCarExtraOption.marketplaceCarExtraOption;

        // 적정 추천과 같은 모델이면서 최신 연식, 주행거리 비슷하거나, 옵션 개수가 더 많은 것 중 2가지 해당
        // 기본 옵션 개수
        /*JPAQuery<Tuple> baseOption = queryFactory.select(carOption.marketplaceCar.id, carOption.count())
                .from(carOption)
                .where(carOption.isPresent.isTrue())
                .groupBy(carOption.marketplaceCar);
        // 추가 옵션 개수
        List<Tuple> extraOption = queryFactory.select(
                carExtraOption.marketplaceCar.id,
                        carExtraOption.count()
                )
                .from(carExtraOption)
                .groupBy(carExtraOption.marketplaceCar)
                .fetch();*/


        // 적정 추천과 같은 모델이면서 최신 연식 + (적정 추천의 주행거리 + 5000km) 이하인 것
        return queryFactory.select(marketplaceCar)
                .from(marketplaceCar)
                .where(marketplaceCar.carDetails.model.eq(car.getCarDetails().getModel())
                        .and(marketplaceCar.price.between(budget+1, budget*1.1))
                        .and(marketplaceCar.carDetails.modelYear.goe(car.getCarDetails().getModelYear())
                                .or(marketplaceCar.carDetails.mileage.loe(car.getCarDetails().getMileage()+5000))))
                .orderBy(marketplaceCar.carDetails.modelYear.asc(), marketplaceCar.price.asc())
                .limit(1)
                .fetchOne();
    }
}
