package com.danialross.ServiceCycle.vehicles.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateVehicleDTO {
    @NotBlank(message = "Vehicle Id is required")
    String Id;

    String make;

    String model;

    String licensePlate;

    @Pattern(regexp = "CAR|MOTORCYCLE", message = "Invalid vehicle type. Valid options: CAR, MOTORCYCLE")
    String type;
}
