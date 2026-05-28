package com.danialross.ServiceCycle.modules.vehicles.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class UpdateVehicleDTO {
    @Schema(description = "Vehicle ID")
    @NotBlank(message = "Vehicle Id is required")
    String Id;

    @Schema(description = "Make of Vehicle")
    String make;

    @Schema(description = "Model of Vehicle")
    String model;

    @Schema(description = "License plate of Vehicle")
    String licensePlate;

    @Schema(description = "Mileage of Vehicle")
    @PositiveOrZero(message = "Mileage must be more than 0")
    Integer mileage;

    @Schema(description = "Type of Vehicle")
    @Pattern(regexp = "CAR|MOTORCYCLE", message = "Invalid vehicle type. Valid options: CAR, MOTORCYCLE")
    String type;
}
