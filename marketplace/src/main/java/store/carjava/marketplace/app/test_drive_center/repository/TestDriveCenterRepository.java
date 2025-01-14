package store.carjava.marketplace.app.test_drive_center.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.carjava.marketplace.app.test_drive_center.entity.TestDriveCenter;

public interface TestDriveCenterRepository extends JpaRepository<TestDriveCenter, Long> {
}
