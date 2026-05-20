package com.danialross.ServiceCycle.modules.vehicles.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateVehicleDTO {

    @NotBlank(message = "Make is required")
    String make;

    @NotBlank(message = "Model is required")
    String model;

    @NotBlank(message = "License plate is required")
    String licensePlate;

    @NotNull(message = "Mileage plate is required")
    @PositiveOrZero(message = "Mileage must be more than 0")
    Integer mileage;

    @Pattern(regexp = "CAR|MOTORCYCLE", message = "Invalid vehicle type. Valid options: CAR, MOTORCYCLE")
    String type;
}
