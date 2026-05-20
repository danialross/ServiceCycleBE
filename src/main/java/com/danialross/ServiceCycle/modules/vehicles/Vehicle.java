package com.danialross.ServiceCycle.modules.vehicles;

import com.danialross.ServiceCycle.modules.parts.Part;
import com.danialross.ServiceCycle.modules.vehicles.enums.VehicleType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;



@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name ="vehicles")
@Schema(description = "Vehicle entity")
public class Vehicle{
    @Id
    @GeneratedValue()
    @Schema(description = "Vehicle's unique UUID")
    private UUID id;

    @Column(nullable = false)
    private String make;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VehicleType type;

    @Column(nullable = false)
    private UUID ownerId;

    @Column(nullable = false,unique = true)
    private String licensePlate;

    @Column(nullable = false)
    private Integer mileage;

    @OneToMany(mappedBy = "vehicle",cascade = CascadeType.ALL)
    private List<Part> parts;
}

