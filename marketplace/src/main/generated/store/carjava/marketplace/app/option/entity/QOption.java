package store.carjava.marketplace.app.option.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOption is a Querydsl query type for Option
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOption extends EntityPathBase<Option> {

    private static final long serialVersionUID = -1447165412L;

    public static final QOption option = new QOption("option");

    public final ListPath<store.carjava.marketplace.app.base_car_option.entity.BaseCarOption, store.carjava.marketplace.app.base_car_option.entity.QBaseCarOption> baseCarOptions = this.<store.carjava.marketplace.app.base_car_option.entity.BaseCarOption, store.carjava.marketplace.app.base_car_option.entity.QBaseCarOption>createList("baseCarOptions", store.carjava.marketplace.app.base_car_option.entity.BaseCarOption.class, store.carjava.marketplace.app.base_car_option.entity.QBaseCarOption.class, PathInits.DIRECT2);

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath image = createString("image");

    public final ListPath<store.carjava.marketplace.app.marketplace_car_option.entity.MarketplaceCarOption, store.carjava.marketplace.app.marketplace_car_option.entity.QMarketplaceCarOption> marketplaceCarOptions = this.<store.carjava.marketplace.app.marketplace_car_option.entity.MarketplaceCarOption, store.carjava.marketplace.app.marketplace_car_option.entity.QMarketplaceCarOption>createList("marketplaceCarOptions", store.carjava.marketplace.app.marketplace_car_option.entity.MarketplaceCarOption.class, store.carjava.marketplace.app.marketplace_car_option.entity.QMarketplaceCarOption.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public QOption(String variable) {
        super(Option.class, forVariable(variable));
    }

    public QOption(Path<? extends Option> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOption(PathMetadata metadata) {
        super(Option.class, metadata);
    }

}

