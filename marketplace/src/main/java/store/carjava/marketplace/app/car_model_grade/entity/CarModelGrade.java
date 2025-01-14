package store.carjava.marketplace.app.car_model_grade.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "car_model_grade")
public class CarModelGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vehicle_type", nullable = false)
    private String vehicleType;

    @Column(name = "model", nullable = false)
    private String model;
    
    @Column(name = "grade")
    private Integer grade;
}
