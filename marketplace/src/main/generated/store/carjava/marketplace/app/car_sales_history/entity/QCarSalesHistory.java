package store.carjava.marketplace.app.car_sales_history.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCarSalesHistory is a Querydsl query type for CarSalesHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCarSalesHistory extends EntityPathBase<CarSalesHistory> {

    private static final long serialVersionUID = 282083262L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCarSalesHistory carSalesHistory = new QCarSalesHistory("carSalesHistory");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final store.carjava.marketplace.app.marketplace_car.entity.QMarketplaceCar marketplaceCar;

    public final store.carjava.marketplace.app.user.entity.QUser user;

    public QCarSalesHistory(String variable) {
        this(CarSalesHistory.class, forVariable(variable), INITS);
    }

    public QCarSalesHistory(Path<? extends CarSalesHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCarSalesHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCarSalesHistory(PathMetadata metadata, PathInits inits) {
        this(CarSalesHistory.class, metadata, inits);
    }

    public QCarSalesHistory(Class<? extends CarSalesHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.marketplaceCar = inits.isInitialized("marketplaceCar") ? new store.carjava.marketplace.app.marketplace_car.entity.QMarketplaceCar(forProperty("marketplaceCar"), inits.get("marketplaceCar")) : null;
        this.user = inits.isInitialized("user") ? new store.carjava.marketplace.app.user.entity.QUser(forProperty("user")) : null;
    }

}

