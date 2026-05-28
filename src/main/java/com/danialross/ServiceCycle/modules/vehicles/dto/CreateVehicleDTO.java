package com.danialross.ServiceCycle.modules.vehicles.dto;

import com.danialross.ServiceCycle.modules.vehicles.Vehicle;
import com.danialross.ServiceCycle.modules.vehicles.enums.VehicleType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.UUID;

@Data
public class CreateVehicleDTO {

    @Schema(description = "Make of Vehicle")
    @NotBlank(message = "Make is required")
    private String make;

    @Schema(description = "Model of Vehicle")
    @NotBlank(message = "Model is required")
    private String model;

    @Schema(description = "License plate of Vehicle")
    @NotBlank(message = "License plate is required")
    private String licensePlate;

    @Schema(description = "Mileage of Vehicle")
    @NotNull(message = "Mileage  is required")
    @PositiveOrZero(message = "Mileage must be more than 0")
    private Integer mileage;

    @Schema(description = "Type of Vehicle")
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
