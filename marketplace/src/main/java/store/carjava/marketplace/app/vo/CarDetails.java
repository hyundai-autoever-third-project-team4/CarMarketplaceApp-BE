package store.carjava.marketplace.app.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarDetails {

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "drive_type", nullable = false)
    private String driveType;

    @Column(name = "engine_capacity")
    private Integer engineCapacity;

    @Column(name = "exterior_color", nullable = false)
    private String exteriorColor;

    @Column(name = "interior_color", nullable = false)
    private String interiorColor;

    @Column(name = "color_type", nullable = false)
    private String colorType;

    @Column(name = "fuel_type", nullable = false)
    private String fuelType;

    @Column(name = "license_plate", nullable = false)
    private String licensePlate;

    @Column(name = "mileage", nullable = false)
    private int mileage;

    @Column(name = "model_year", nullable = false)
    private int modelYear;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "seating_capacity", nullable = false)
    private int seatingCapacity;

    @Column(name = "transmission")
    private String transmission;

    @Column(name = "vehicle_type", nullable = false)
    private String vehicleType;
}
