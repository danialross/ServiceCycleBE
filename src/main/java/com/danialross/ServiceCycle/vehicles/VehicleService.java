package com.danialross.ServiceCycle.vehicles;

import com.danialross.ServiceCycle.vehicles.dto.CreateVehicleDTO;
import com.danialross.ServiceCycle.vehicles.dto.UpdateVehicleDTO;
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

    public VehicleResponse update(UUID ownerId, UpdateVehicleDTO vehicle){
        UUID vehicleId = UUID.fromString(vehicle.getId());

        Vehicle existingVehicle = vehicleRepository.findByIdAndOwnerId(vehicleId,ownerId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, ownerId + " does not own a vehicle with ID: " + vehicle.getId()));

        if( vehicle.getMake() != null){
            existingVehicle.setMake(vehicle.getMake());
        }

        if( vehicle.getModel() != null){
            existingVehicle.setModel(vehicle.getModel());
        }

        if( vehicle.getType() != null){
            existingVehicle.setType(VehicleType.valueOf(vehicle.getType()));
        }

        if( vehicle.getLicensePlate() != null){
            existingVehicle.setLicensePlate(vehicle.getLicensePlate());
        }

        Vehicle savedVehicle = vehicleRepository.save(existingVehicle);

        return VehicleResponse.fromVehicle(savedVehicle);
    }


    public VehicleResponse delete(UUID ownerId,UUID vehicleId){
        Vehicle deletingVehicle = vehicleRepository.findByIdAndOwnerId(vehicleId,ownerId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,ownerId + " does not have a vehicle with id: " + vehicleId + " to be deleted"));
        vehicleRepository.delete(deletingVehicle);
        return VehicleResponse.fromVehicle(deletingVehicle);
    }
}
