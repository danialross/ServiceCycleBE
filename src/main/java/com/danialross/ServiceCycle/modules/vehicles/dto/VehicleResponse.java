package com.danialross.ServiceCycle.modules.vehicles.dto;

import com.danialross.ServiceCycle.modules.vehicles.Vehicle;
import com.danialross.ServiceCycle.modules.vehicles.enums.VehicleType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class VehicleResponse {
    @Schema(description = "Vehicle ID")
    private UUID id;
    @Schema(description = "Make of Vehicle")
    private String make;
    @Schema(description = "Model of Vehicle")
    private String model;
    @Schema(description = "License Plate of Vehicle")
    private String licensePlate;
    @Schema(description = "Type of Vehicle")
    private VehicleType type;
    @Schema(description = "Owner ID of Vehicle")
    private UUID ownerId;

    public static VehicleResponse fromVehicle(Vehicle vehicle){
            return VehicleResponse.builder()
                    .id(vehicle.getId())
                    .make(vehicle.getMake())
                    .model(vehicle.getModel())
                    .licensePlate(vehicle.getLicensePlate())
                    .type(vehicle.getType())
                    .ownerId(vehicle.getOwnerId())
                    .build();
    }
}
