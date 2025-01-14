package store.carjava.marketplace.app.base_car.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.carjava.marketplace.app.base_car.entity.BaseCar;

import java.util.List;
import java.util.Optional;

public interface BaseCarRepository extends JpaRepository<BaseCar, String> {
    Optional<BaseCar> findByCarDetailsLicensePlateAndOwnerName(String licensePlate, String ownerName);
}
