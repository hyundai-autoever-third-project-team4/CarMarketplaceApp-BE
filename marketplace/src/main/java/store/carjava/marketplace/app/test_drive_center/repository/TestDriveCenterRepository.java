package store.carjava.marketplace.app.test_drive_center.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import store.carjava.marketplace.app.test_drive_center.entity.TestDriveCenter;

import java.util.Optional;

public interface TestDriveCenterRepository extends JpaRepository<TestDriveCenter, Long> {
    Optional<TestDriveCenter> findByName(String name);
}
