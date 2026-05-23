package com.danialross.ServiceCycle.modules.parts;

import com.danialross.ServiceCycle.modules.vehicles.Vehicle;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Table(name = "parts")
@Schema(description = "Vehicle's Part entity")
public class Part {
    @Id
    @GeneratedValue()
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "vehicle_id",nullable = false)
    private Vehicle vehicle;

    @Column()
    private String brand;

    @Column()
    @Enumerated(EnumType.STRING)
    private PartType type;

    @Column()
    private BigDecimal price;

    @Column(nullable = false)
    @Schema(pattern = "numeric value in kilometers")
    private Integer validityDistance;

    @Column(nullable = false)
    @Schema(pattern = "numeric value in months")
    private Integer validityTime;
}
