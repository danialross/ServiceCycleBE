package com.danialross.ServiceCycle.vehicles;

import com.danialross.ServiceCycle.vehicles.dto.CreateVehicleDTO;
import com.danialross.ServiceCycle.vehicles.dto.VehicleResponse;
import com.danialross.ServiceCycle.vehicles.enums.VehicleType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;

    public VehicleResponse register(UUID ownerId, CreateVehicleDTO vehicle){

        if(vehicleRepository.existsByOwnerIdAndLicensePlate(ownerId,vehicle.getLicensePlate())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,ownerId + " already has a vehicle with license plate: " + vehicle.getLicensePlate());
        }

        Vehicle newVehicle = new Vehicle();
        newVehicle.setOwnerId(ownerId);
        newVehicle.setMake(vehicle.getMake());
        newVehicle.setModel(vehicle.getModel());
        newVehicle.setType(VehicleType.valueOf(vehicle.getType()));
        newVehicle.setLicensePlate(vehicle.getLicensePlate());

        Vehicle savedVehicle = vehicleRepository.save(newVehicle);

        return VehicleResponse.fromVehicle(savedVehicle);
    }
}
