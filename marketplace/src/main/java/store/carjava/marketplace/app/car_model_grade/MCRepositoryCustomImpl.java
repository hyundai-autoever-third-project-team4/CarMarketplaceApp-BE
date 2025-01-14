package store.carjava.marketplace.app.car_model_grade;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import store.carjava.marketplace.app.car_model_grade.entity.QCarModelGrade;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;
import store.carjava.marketplace.app.marketplace_car.entity.QMarketplaceCar;
import store.carjava.marketplace.app.marketplace_car_extra_option.entity.QMarketplaceCarExtraOption;
import store.carjava.marketplace.app.marketplace_car_option.entity.QMarketplaceCarOption;

import java.util.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MCRepositoryCustomImpl implements MCRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<MarketplaceCar> findMarketplaceCarProperList(long budgetLow, long budgetHigh, String vehicle) {
        QMarketplaceCar marketplaceCar = QMarketplaceCar.marketplaceCar;
        QCarModelGrade carModelGrade = QCarModelGrade.carModelGrade;

        // 예산 범위 내 가장 많은 차종 구하기
        String carmodel = queryFactory.select(marketplaceCar.carDetails.model)
                .from(marketplaceCar)
                .leftJoin(carModelGrade).on(marketplaceCar.carDetails.model.eq(carModelGrade.model))
                .where(
                        marketplaceCar.price.between(budgetLow, budgetHigh)
                                .and(marketplaceCar.carDetails.vehicleType.eq(vehicle))
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

    @Override
    public MarketplaceCar findUpgradeModelCarOverPrice(long budget, String vehicle, MarketplaceCar car) {
        QMarketplaceCar marketplaceCar = QMarketplaceCar.marketplaceCar;
        QCarModelGrade carModelGrade = QCarModelGrade.carModelGrade;

        Integer carGrade = queryFactory.select(carModelGrade.grade).from(carModelGrade)
                .where(carModelGrade.model.eq(car.getCarDetails().getModel()))
                .fetchOne();

        // 적정 추천보다 고급 모델이면서 주행거리가 비슷한 차량
        return queryFactory.selectFrom(marketplaceCar)
                .leftJoin(carModelGrade)
                .on(marketplaceCar.carDetails.model.eq(carModelGrade.model))
                .where(marketplaceCar.carDetails.vehicleType.eq(vehicle)
                        .and(marketplaceCar.price.gt(budget))
                        .and(carModelGrade.model.in(
                                JPAExpressions.select(carModelGrade.model)
                                .from(carModelGrade)
                                .where(carModelGrade.vehicleType.eq(vehicle)
                                        .and(carModelGrade.grade.gt(carGrade))
                                )
                        ))
                )
                .orderBy(marketplaceCar.price.asc())
                .limit(1)
                .fetchOne();
    }

    @Override
    public MarketplaceCar findCarMoreOptionOverPrice(long budget, String vehicle, MarketplaceCar car) {
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


        // 적정 추천과 같은 모델이면서 최신 연식, 주행거리 비슷한 것
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
