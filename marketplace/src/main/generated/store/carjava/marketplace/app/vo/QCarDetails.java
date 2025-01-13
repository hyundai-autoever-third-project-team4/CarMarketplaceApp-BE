package store.carjava.marketplace.app.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCarDetails is a Querydsl query type for CarDetails
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QCarDetails extends BeanPath<CarDetails> {

    private static final long serialVersionUID = -200554226L;

    public static final QCarDetails carDetails = new QCarDetails("carDetails");

    public final StringPath brand = createString("brand");

    public final StringPath colorType = createString("colorType");

    public final StringPath driveType = createString("driveType");

    public final NumberPath<Integer> engineCapacity = createNumber("engineCapacity", Integer.class);

    public final StringPath exteriorColor = createString("exteriorColor");

    public final StringPath fuelType = createString("fuelType");

    public final StringPath interiorColor = createString("interiorColor");

    public final StringPath licensePlate = createString("licensePlate");

    public final NumberPath<Integer> mileage = createNumber("mileage", Integer.class);

    public final StringPath model = createString("model");

    public final NumberPath<Integer> modelYear = createNumber("modelYear", Integer.class);

    public final StringPath name = createString("name");

    public final DatePath<java.time.LocalDate> registrationDate = createDate("registrationDate", java.time.LocalDate.class);

    public final NumberPath<Integer> seatingCapacity = createNumber("seatingCapacity", Integer.class);

    public final StringPath transmission = createString("transmission");

    public final StringPath vehicleType = createString("vehicleType");

    public QCarDetails(String variable) {
        super(CarDetails.class, forVariable(variable));
    }

    public QCarDetails(Path<? extends CarDetails> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCarDetails(PathMetadata metadata) {
        super(CarDetails.class, metadata);
    }

}

