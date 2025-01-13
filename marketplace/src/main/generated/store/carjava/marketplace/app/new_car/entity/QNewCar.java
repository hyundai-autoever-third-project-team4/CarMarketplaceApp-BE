package store.carjava.marketplace.app.new_car.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNewCar is a Querydsl query type for NewCar
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNewCar extends EntityPathBase<NewCar> {

    private static final long serialVersionUID = 923615473L;

    public static final QNewCar newCar = new QNewCar("newCar");

    public final NumberPath<Long> averagePrice = createNumber("averagePrice", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QNewCar(String variable) {
        super(NewCar.class, forVariable(variable));
    }

    public QNewCar(Path<? extends NewCar> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNewCar(PathMetadata metadata) {
        super(NewCar.class, metadata);
    }

}

