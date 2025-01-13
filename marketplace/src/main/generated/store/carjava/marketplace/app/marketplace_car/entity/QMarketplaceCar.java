package store.carjava.marketplace.app.marketplace_car.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMarketplaceCar is a Querydsl query type for MarketplaceCar
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMarketplaceCar extends EntityPathBase<MarketplaceCar> {

    private static final long serialVersionUID = -1140914863L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMarketplaceCar marketplaceCar = new QMarketplaceCar("marketplaceCar");

    public final store.carjava.marketplace.app.vo.QCarDetails carDetails;

    public final ListPath<store.carjava.marketplace.app.car_purchase_history.entity.CarPurchaseHistory, store.carjava.marketplace.app.car_purchase_history.entity.QCarPurchaseHistory> CarPurchaseHistories = this.<store.carjava.marketplace.app.car_purchase_history.entity.CarPurchaseHistory, store.carjava.marketplace.app.car_purchase_history.entity.QCarPurchaseHistory>createList("CarPurchaseHistories", store.carjava.marketplace.app.car_purchase_history.entity.CarPurchaseHistory.class, store.carjava.marketplace.app.car_purchase_history.entity.QCarPurchaseHistory.class, PathInits.DIRECT2);

    public final ListPath<store.carjava.marketplace.app.car_sales_history.entity.CarSalesHistory, store.carjava.marketplace.app.car_sales_history.entity.QCarSalesHistory> carSalesHistories = this.<store.carjava.marketplace.app.car_sales_history.entity.CarSalesHistory, store.carjava.marketplace.app.car_sales_history.entity.QCarSalesHistory>createList("carSalesHistories", store.carjava.marketplace.app.car_sales_history.entity.CarSalesHistory.class, store.carjava.marketplace.app.car_sales_history.entity.QCarSalesHistory.class, PathInits.DIRECT2);

    public final StringPath id = createString("id");

    public final ListPath<store.carjava.marketplace.app.like.entity.Like, store.carjava.marketplace.app.like.entity.QLike> likes = this.<store.carjava.marketplace.app.like.entity.Like, store.carjava.marketplace.app.like.entity.QLike>createList("likes", store.carjava.marketplace.app.like.entity.Like.class, store.carjava.marketplace.app.like.entity.QLike.class, PathInits.DIRECT2);

    public final StringPath mainImage = createString("mainImage");

    public final ListPath<store.carjava.marketplace.app.marketplace_car_extra_option.entity.MarketplaceCarExtraOption, store.carjava.marketplace.app.marketplace_car_extra_option.entity.QMarketplaceCarExtraOption> marketplaceCarExtraOptions = this.<store.carjava.marketplace.app.marketplace_car_extra_option.entity.MarketplaceCarExtraOption, store.carjava.marketplace.app.marketplace_car_extra_option.entity.QMarketplaceCarExtraOption>createList("marketplaceCarExtraOptions", store.carjava.marketplace.app.marketplace_car_extra_option.entity.MarketplaceCarExtraOption.class, store.carjava.marketplace.app.marketplace_car_extra_option.entity.QMarketplaceCarExtraOption.class, PathInits.DIRECT2);

    public final ListPath<store.carjava.marketplace.app.marketplace_car_image.entity.MarketplaceCarImage, store.carjava.marketplace.app.marketplace_car_image.entity.QMarketplaceCarImage> marketplaceCarImages = this.<store.carjava.marketplace.app.marketplace_car_image.entity.MarketplaceCarImage, store.carjava.marketplace.app.marketplace_car_image.entity.QMarketplaceCarImage>createList("marketplaceCarImages", store.carjava.marketplace.app.marketplace_car_image.entity.MarketplaceCarImage.class, store.carjava.marketplace.app.marketplace_car_image.entity.QMarketplaceCarImage.class, PathInits.DIRECT2);

    public final ListPath<store.carjava.marketplace.app.marketplace_car_option.entity.MarketplaceCarOption, store.carjava.marketplace.app.marketplace_car_option.entity.QMarketplaceCarOption> marketplaceCarOptions = this.<store.carjava.marketplace.app.marketplace_car_option.entity.MarketplaceCarOption, store.carjava.marketplace.app.marketplace_car_option.entity.QMarketplaceCarOption>createList("marketplaceCarOptions", store.carjava.marketplace.app.marketplace_car_option.entity.MarketplaceCarOption.class, store.carjava.marketplace.app.marketplace_car_option.entity.QMarketplaceCarOption.class, PathInits.DIRECT2);

    public final DatePath<java.time.LocalDate> marketplaceRegistrationDate = createDate("marketplaceRegistrationDate", java.time.LocalDate.class);

    public final NumberPath<Long> price = createNumber("price", Long.class);

    public final ListPath<store.carjava.marketplace.app.reservation.entity.Reservation, store.carjava.marketplace.app.reservation.entity.QReservation> reservations = this.<store.carjava.marketplace.app.reservation.entity.Reservation, store.carjava.marketplace.app.reservation.entity.QReservation>createList("reservations", store.carjava.marketplace.app.reservation.entity.Reservation.class, store.carjava.marketplace.app.reservation.entity.QReservation.class, PathInits.DIRECT2);

    public final StringPath status = createString("status");

    public final store.carjava.marketplace.app.test_drive_center.entity.QTestDriveCenter testDriveCenter;

    public QMarketplaceCar(String variable) {
        this(MarketplaceCar.class, forVariable(variable), INITS);
    }

    public QMarketplaceCar(Path<? extends MarketplaceCar> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMarketplaceCar(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMarketplaceCar(PathMetadata metadata, PathInits inits) {
        this(MarketplaceCar.class, metadata, inits);
    }

    public QMarketplaceCar(Class<? extends MarketplaceCar> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.carDetails = inits.isInitialized("carDetails") ? new store.carjava.marketplace.app.vo.QCarDetails(forProperty("carDetails")) : null;
        this.testDriveCenter = inits.isInitialized("testDriveCenter") ? new store.carjava.marketplace.app.test_drive_center.entity.QTestDriveCenter(forProperty("testDriveCenter")) : null;
    }

}

