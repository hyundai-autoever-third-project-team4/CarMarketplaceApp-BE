package store.carjava.marketplace.app.marketplace_car_option.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMarketplaceCarOption is a Querydsl query type for MarketplaceCarOption
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMarketplaceCarOption extends EntityPathBase<MarketplaceCarOption> {

    private static final long serialVersionUID = 1258061700L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMarketplaceCarOption marketplaceCarOption = new QMarketplaceCarOption("marketplaceCarOption");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isPresent = createBoolean("isPresent");

    public final store.carjava.marketplace.app.marketplace_car.entity.QMarketplaceCar marketplaceCar;

    public final store.carjava.marketplace.app.option.entity.QOption option;

    public QMarketplaceCarOption(String variable) {
        this(MarketplaceCarOption.class, forVariable(variable), INITS);
    }

    public QMarketplaceCarOption(Path<? extends MarketplaceCarOption> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMarketplaceCarOption(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMarketplaceCarOption(PathMetadata metadata, PathInits inits) {
        this(MarketplaceCarOption.class, metadata, inits);
    }

    public QMarketplaceCarOption(Class<? extends MarketplaceCarOption> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.marketplaceCar = inits.isInitialized("marketplaceCar") ? new store.carjava.marketplace.app.marketplace_car.entity.QMarketplaceCar(forProperty("marketplaceCar"), inits.get("marketplaceCar")) : null;
        this.option = inits.isInitialized("option") ? new store.carjava.marketplace.app.option.entity.QOption(forProperty("option")) : null;
    }

}

