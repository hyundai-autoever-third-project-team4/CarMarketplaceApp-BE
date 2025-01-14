package store.carjava.marketplace.app.review.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReview is a Querydsl query type for Review
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReview extends EntityPathBase<Review> {

    private static final long serialVersionUID = 1895667362L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReview review = new QReview("review");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final store.carjava.marketplace.app.marketplace_car.entity.QMarketplaceCar marketplaceCar;

    public final StringPath model = createString("model");

    public final ListPath<store.carjava.marketplace.app.review_image.entity.ReviewImage, store.carjava.marketplace.app.review_image.entity.QReviewImage> ReviewImages = this.<store.carjava.marketplace.app.review_image.entity.ReviewImage, store.carjava.marketplace.app.review_image.entity.QReviewImage>createList("ReviewImages", store.carjava.marketplace.app.review_image.entity.ReviewImage.class, store.carjava.marketplace.app.review_image.entity.QReviewImage.class, PathInits.DIRECT2);

    public final NumberPath<Double> starRate = createNumber("starRate", Double.class);

    public final store.carjava.marketplace.app.user.entity.QUser user;

    public QReview(String variable) {
        this(Review.class, forVariable(variable), INITS);
    }

    public QReview(Path<? extends Review> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReview(PathMetadata metadata, PathInits inits) {
        this(Review.class, metadata, inits);
    }

    public QReview(Class<? extends Review> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.marketplaceCar = inits.isInitialized("marketplaceCar") ? new store.carjava.marketplace.app.marketplace_car.entity.QMarketplaceCar(forProperty("marketplaceCar"), inits.get("marketplaceCar")) : null;
        this.user = inits.isInitialized("user") ? new store.carjava.marketplace.app.user.entity.QUser(forProperty("user")) : null;
    }

}

