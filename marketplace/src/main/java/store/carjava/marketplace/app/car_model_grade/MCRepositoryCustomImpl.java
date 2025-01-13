package store.carjava.marketplace.app.car_model_grade;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import store.carjava.marketplace.app.car_model_grade.entity.QCarModelGrade;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;
import store.carjava.marketplace.app.marketplace_car.entity.QMarketplaceCar;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MCRepositoryCustomImpl implements MCRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<MarketplaceCar> findMarketplaceCarList(long budgetLow, long budgetHigh, String vehicle) {
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
                .groupBy(marketplaceCar.carDetails.model, carModelGrade.model)
                .orderBy(
                        marketplaceCar.carDetails.model.count().desc(),
                        carModelGrade.grade.desc()
                )
                .limit(1)
                .fetchOne();


        return queryFactory
                .selectFrom(marketplaceCar)
                .where(marketplaceCar.carDetails.model.eq(carmodel)
                        .and(marketplaceCar.price.between(budgetLow, budgetHigh)))
                .fetch();
    }
}
