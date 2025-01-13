package store.carjava.marketplace.app.marketplace_car_extra_option.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMarketplaceCarExtraOption is a Querydsl query type for MarketplaceCarExtraOption
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMarketplaceCarExtraOption extends EntityPathBase<MarketplaceCarExtraOption> {

    private static final long serialVersionUID = -711848761L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMarketplaceCarExtraOption marketplaceCarExtraOption = new QMarketplaceCarExtraOption("marketplaceCarExtraOption");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final store.carjava.marketplace.app.marketplace_car.entity.QMarketplaceCar marketplaceCar;

    public final StringPath name = createString("name");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public QMarketplaceCarExtraOption(String variable) {
        this(MarketplaceCarExtraOption.class, forVariable(variable), INITS);
    }

    public QMarketplaceCarExtraOption(Path<? extends MarketplaceCarExtraOption> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMarketplaceCarExtraOption(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMarketplaceCarExtraOption(PathMetadata metadata, PathInits inits) {
        this(MarketplaceCarExtraOption.class, metadata, inits);
    }

    public QMarketplaceCarExtraOption(Class<? extends MarketplaceCarExtraOption> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.marketplaceCar = inits.isInitialized("marketplaceCar") ? new store.carjava.marketplace.app.marketplace_car.entity.QMarketplaceCar(forProperty("marketplaceCar"), inits.get("marketplaceCar")) : null;
    }

}

