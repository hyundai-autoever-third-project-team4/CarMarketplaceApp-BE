package store.carjava.marketplace.app.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -128338232L;

    public static final QUser user = new QUser("user");

    public final StringPath address = createString("address");

    public final ListPath<store.carjava.marketplace.app.car_purchase_history.entity.CarPurchaseHistory, store.carjava.marketplace.app.car_purchase_history.entity.QCarPurchaseHistory> carPurchaseHistories = this.<store.carjava.marketplace.app.car_purchase_history.entity.CarPurchaseHistory, store.carjava.marketplace.app.car_purchase_history.entity.QCarPurchaseHistory>createList("carPurchaseHistories", store.carjava.marketplace.app.car_purchase_history.entity.CarPurchaseHistory.class, store.carjava.marketplace.app.car_purchase_history.entity.QCarPurchaseHistory.class, PathInits.DIRECT2);

    public final ListPath<store.carjava.marketplace.app.car_sales_history.entity.CarSalesHistory, store.carjava.marketplace.app.car_sales_history.entity.QCarSalesHistory> carSalesHistories = this.<store.carjava.marketplace.app.car_sales_history.entity.CarSalesHistory, store.carjava.marketplace.app.car_sales_history.entity.QCarSalesHistory>createList("carSalesHistories", store.carjava.marketplace.app.car_sales_history.entity.CarSalesHistory.class, store.carjava.marketplace.app.car_sales_history.entity.QCarSalesHistory.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<store.carjava.marketplace.app.like.entity.Like, store.carjava.marketplace.app.like.entity.QLike> likes = this.<store.carjava.marketplace.app.like.entity.Like, store.carjava.marketplace.app.like.entity.QLike>createList("likes", store.carjava.marketplace.app.like.entity.Like.class, store.carjava.marketplace.app.like.entity.QLike.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final StringPath phone = createString("phone");

    public final ListPath<store.carjava.marketplace.app.reservation.entity.Reservation, store.carjava.marketplace.app.reservation.entity.QReservation> reservations = this.<store.carjava.marketplace.app.reservation.entity.Reservation, store.carjava.marketplace.app.reservation.entity.QReservation>createList("reservations", store.carjava.marketplace.app.reservation.entity.Reservation.class, store.carjava.marketplace.app.reservation.entity.QReservation.class, PathInits.DIRECT2);

    public final ListPath<store.carjava.marketplace.app.review.entity.Review, store.carjava.marketplace.app.review.entity.QReview> reviews = this.<store.carjava.marketplace.app.review.entity.Review, store.carjava.marketplace.app.review.entity.QReview>createList("reviews", store.carjava.marketplace.app.review.entity.Review.class, store.carjava.marketplace.app.review.entity.QReview.class, PathInits.DIRECT2);

    public final StringPath role = createString("role");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

