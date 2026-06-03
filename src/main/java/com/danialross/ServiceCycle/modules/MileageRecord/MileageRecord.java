package com.danialross.ServiceCycle.modules.MileageRecord;

import com.danialross.ServiceCycle.modules.MileageRecord.dto.MileageResponse;
import com.danialross.ServiceCycle.modules.vehicles.Vehicle;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "mileage_record")
public class MileageRecord {
    @Id
    @GeneratedValue
    @Schema(description = "ID of the records")
    private UUID id;

    @Column(nullable = false)
    @Schema(description = "Mileage of the records")
    private Integer mileage;

    @Column(nullable = false)
    @Schema(description = "Date of the records")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "vehicle_id",nullable = false)
    @Schema(description = "Vehicle of the records")
    private Vehicle vehicle;
}
