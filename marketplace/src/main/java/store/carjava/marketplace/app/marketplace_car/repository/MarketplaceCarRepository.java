package store.carjava.marketplace.app.marketplace_car.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;
import store.carjava.marketplace.app.test_drive_center.entity.TestDriveCenter;

import java.util.List;
import java.util.Optional;

import java.util.List;

@Repository
public interface MarketplaceCarRepository extends JpaRepository<MarketplaceCar, String>, MarketplaceCarCustomRepository {
    List<MarketplaceCar> findAllByTestDriveCenterAndStatus(TestDriveCenter testDriveCenter, String status);

    List<MarketplaceCar> findByStatus(String status);

    Optional<MarketplaceCar> findTopByCarDetails_VehicleTypeInAndPriceLessThanEqualOrderByPriceDesc(List<String> vehicle, Long price);

    List<MarketplaceCar> findTop2ByCarDetails_VehicleTypeInAndPriceLessThanEqualOrderByPriceDesc(List<String> vehicle, Long price);

    Optional<MarketplaceCar> findTopByPriceGreaterThanOrderByPrice(Long price);


    @Query("SELECT car FROM MarketplaceCar car " +
            "LEFT JOIN car.likes likes " +
            "GROUP BY car.id " +
            "ORDER BY COUNT(likes) DESC limit 5")
    List<MarketplaceCar> findTop5ByLikeCount();

    @Query("SELECT m FROM MarketplaceCar m WHERE m.carDetails.licensePlate LIKE CONCAT(:licensePlate, '%')")
    Page<MarketplaceCar> findByLicensePlateStartingWith(@Param("licensePlate") String licensePlate, Pageable pageable);

}
