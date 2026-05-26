package com.danialross.ServiceCycle.modules.vehicles.dto;

import com.danialross.ServiceCycle.modules.vehicles.Vehicle;
import com.danialross.ServiceCycle.modules.vehicles.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class VehicleResponse {
    private UUID id;
    private String make;
    private String model;
    private String licensePlate;
    private VehicleType type;
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
