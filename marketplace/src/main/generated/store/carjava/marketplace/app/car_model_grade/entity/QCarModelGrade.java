package store.carjava.marketplace.app.car_model_grade.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCarModelGrade is a Querydsl query type for CarModelGrade
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCarModelGrade extends EntityPathBase<CarModelGrade> {

    private static final long serialVersionUID = 622962340L;

    public static final QCarModelGrade carModelGrade = new QCarModelGrade("carModelGrade");

    public final NumberPath<Integer> grade = createNumber("grade", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath model = createString("model");

    public final StringPath vehicleType = createString("vehicleType");

    public QCarModelGrade(String variable) {
        super(CarModelGrade.class, forVariable(variable));
    }

    public QCarModelGrade(Path<? extends CarModelGrade> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCarModelGrade(PathMetadata metadata) {
        super(CarModelGrade.class, metadata);
    }

}

