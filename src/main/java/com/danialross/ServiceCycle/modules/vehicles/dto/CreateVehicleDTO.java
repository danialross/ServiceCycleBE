package com.danialross.ServiceCycle.modules.vehicles.dto;

import com.danialross.ServiceCycle.modules.vehicles.Vehicle;
import com.danialross.ServiceCycle.modules.vehicles.enums.VehicleType;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.UUID;

@Data
public class CreateVehicleDTO {

    @NotBlank(message = "Make is required")
    private String make;

    @NotBlank(message = "Model is required")
    private String model;

    @NotBlank(message = "License plate is required")
    private String licensePlate;

    @NotNull(message = "Mileage plate is required")
    @PositiveOrZero(message = "Mileage must be more than 0")
    private Integer mileage;

    @Pattern(regexp = "CAR|MOTORCYCLE", message = "Invalid vehicle type. Valid options: CAR, MOTORCYCLE")
    private String type;

    public Vehicle toEntity(UUID ownerId){
        return Vehicle.builder()
                .ownerId(ownerId)
                .make(this.getMake())
                .model(this.getModel())
                .type(VehicleType.valueOf(this.getType()))
                .licensePlate(this.getLicensePlate())
                .build();
    }
}
