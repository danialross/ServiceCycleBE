package com.danialross.ServiceCycle.vehicles.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class VehicleQueryDTO {
    String make;
    String model;

    @Pattern(regexp = "CAR|MOTORCYCLE", message = "Invalid vehicle type. Valid options: CAR, MOTORCYCLE")
    String type;

    String licensePlate;
}
