package com.danialross.ServiceCycle.modules.MileageRecord;

import com.danialross.ServiceCycle.modules.vehicles.Vehicle;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@Table(name = "mileage_record")
public class MileageRecord {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private Integer mileage;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne
    @Column(nullable = false)
    @JoinColumn(name = "vehicle_id",nullable = false)
    private Vehicle vehicle;
}
