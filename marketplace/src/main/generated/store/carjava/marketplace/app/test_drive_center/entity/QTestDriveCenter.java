package store.carjava.marketplace.app.test_drive_center.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTestDriveCenter is a Querydsl query type for TestDriveCenter
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTestDriveCenter extends EntityPathBase<TestDriveCenter> {

    private static final long serialVersionUID = 294344014L;

    public static final QTestDriveCenter testDriveCenter = new QTestDriveCenter("testDriveCenter");

    public final StringPath address = createString("address");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final ListPath<store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar, store.carjava.marketplace.app.marketplace_car.entity.QMarketplaceCar> marketplaceCars = this.<store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar, store.carjava.marketplace.app.marketplace_car.entity.QMarketplaceCar>createList("marketplaceCars", store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar.class, store.carjava.marketplace.app.marketplace_car.entity.QMarketplaceCar.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public QTestDriveCenter(String variable) {
        super(TestDriveCenter.class, forVariable(variable));
    }

    public QTestDriveCenter(Path<? extends TestDriveCenter> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTestDriveCenter(PathMetadata metadata) {
        super(TestDriveCenter.class, metadata);
    }

}

