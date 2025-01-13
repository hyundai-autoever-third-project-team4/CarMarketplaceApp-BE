package store.carjava.marketplace.app.base_car_option.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBaseCarOption is a Querydsl query type for BaseCarOption
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBaseCarOption extends EntityPathBase<BaseCarOption> {

    private static final long serialVersionUID = 591073282L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBaseCarOption baseCarOption = new QBaseCarOption("baseCarOption");

    public final store.carjava.marketplace.app.base_car.entity.QBaseCar baseCar;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isPresent = createBoolean("isPresent");

    public final store.carjava.marketplace.app.option.entity.QOption option;

    public QBaseCarOption(String variable) {
        this(BaseCarOption.class, forVariable(variable), INITS);
    }

    public QBaseCarOption(Path<? extends BaseCarOption> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBaseCarOption(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBaseCarOption(PathMetadata metadata, PathInits inits) {
        this(BaseCarOption.class, metadata, inits);
    }

    public QBaseCarOption(Class<? extends BaseCarOption> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.baseCar = inits.isInitialized("baseCar") ? new store.carjava.marketplace.app.base_car.entity.QBaseCar(forProperty("baseCar"), inits.get("baseCar")) : null;
        this.option = inits.isInitialized("option") ? new store.carjava.marketplace.app.option.entity.QOption(forProperty("option")) : null;
    }

}

