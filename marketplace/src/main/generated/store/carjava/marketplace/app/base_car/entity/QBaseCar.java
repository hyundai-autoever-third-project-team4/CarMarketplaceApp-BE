package store.carjava.marketplace.app.base_car.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBaseCar is a Querydsl query type for BaseCar
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBaseCar extends EntityPathBase<BaseCar> {

    private static final long serialVersionUID = -1701561685L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBaseCar baseCar = new QBaseCar("baseCar");

    public final ListPath<store.carjava.marketplace.app.base_car_option.entity.BaseCarOption, store.carjava.marketplace.app.base_car_option.entity.QBaseCarOption> baseCarOptions = this.<store.carjava.marketplace.app.base_car_option.entity.BaseCarOption, store.carjava.marketplace.app.base_car_option.entity.QBaseCarOption>createList("baseCarOptions", store.carjava.marketplace.app.base_car_option.entity.BaseCarOption.class, store.carjava.marketplace.app.base_car_option.entity.QBaseCarOption.class, PathInits.DIRECT2);

    public final store.carjava.marketplace.app.vo.QCarDetails carDetails;

    public final StringPath id = createString("id");

    public QBaseCar(String variable) {
        this(BaseCar.class, forVariable(variable), INITS);
    }

    public QBaseCar(Path<? extends BaseCar> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBaseCar(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBaseCar(PathMetadata metadata, PathInits inits) {
        this(BaseCar.class, metadata, inits);
    }

    public QBaseCar(Class<? extends BaseCar> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.carDetails = inits.isInitialized("carDetails") ? new store.carjava.marketplace.app.vo.QCarDetails(forProperty("carDetails")) : null;
    }

}

