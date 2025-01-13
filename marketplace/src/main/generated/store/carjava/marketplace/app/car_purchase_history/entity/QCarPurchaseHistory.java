package store.carjava.marketplace.app.car_purchase_history.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCarPurchaseHistory is a Querydsl query type for CarPurchaseHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCarPurchaseHistory extends EntityPathBase<CarPurchaseHistory> {

    private static final long serialVersionUID = 1170703666L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCarPurchaseHistory carPurchaseHistory = new QCarPurchaseHistory("carPurchaseHistory");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final store.carjava.marketplace.app.marketplace_car.entity.QMarketplaceCar marketplaceCar;

    public final store.carjava.marketplace.app.user.entity.QUser user;

    public QCarPurchaseHistory(String variable) {
        this(CarPurchaseHistory.class, forVariable(variable), INITS);
    }

    public QCarPurchaseHistory(Path<? extends CarPurchaseHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCarPurchaseHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCarPurchaseHistory(PathMetadata metadata, PathInits inits) {
        this(CarPurchaseHistory.class, metadata, inits);
    }

    public QCarPurchaseHistory(Class<? extends CarPurchaseHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.marketplaceCar = inits.isInitialized("marketplaceCar") ? new store.carjava.marketplace.app.marketplace_car.entity.QMarketplaceCar(forProperty("marketplaceCar"), inits.get("marketplaceCar")) : null;
        this.user = inits.isInitialized("user") ? new store.carjava.marketplace.app.user.entity.QUser(forProperty("user")) : null;
    }

}

