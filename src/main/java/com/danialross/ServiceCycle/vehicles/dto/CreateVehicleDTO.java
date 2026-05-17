package com.danialross.ServiceCycle.vehicles.dto;

import com.danialross.ServiceCycle.vehicles.enums.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateVehicleDTO {

    @NotBlank(message = "Make is required")
    String make;

    @NotBlank(message = "Model is required")
    String model;

    @NotBlank(message = "License plate is required")
    String licensePlate;

    @Pattern(regexp = "CAR|MOTORCYCLE", message = "Invalid vehicle type. Valid options: CAR, MOTORCYCLE")
    @NotNull(message = "Vehicle type is required")
    String type;
}
