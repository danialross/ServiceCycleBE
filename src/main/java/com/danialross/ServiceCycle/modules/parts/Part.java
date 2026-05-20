package com.danialross.ServiceCycle.modules.parts;

import com.danialross.ServiceCycle.modules.vehicles.Vehicle;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

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

    @Column(nullable = false)
    private String name;

    @Column()
    private String brand;

    @Column()
    private PartType type;

    @Column()
    private float price;

    @Column(nullable = false)
    private int validityDistance;

    @Column(nullable = false)
    private int validityTime;


}
