package com.danialross.ServiceCycle.modules.vehicles;

import com.danialross.ServiceCycle.modules.vehicles.dto.CreateVehicleDTO;
import com.danialross.ServiceCycle.modules.vehicles.dto.UpdateVehicleDTO;
import com.danialross.ServiceCycle.modules.vehicles.enums.VehicleType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;

    public Vehicle register(UUID userId, CreateVehicleDTO vehicleDto){

        if(vehicleRepository.existsByOwnerIdAndLicensePlate(userId,vehicleDto.getLicensePlate())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,userId + " already has a vehicle with license plate: " + vehicleDto.getLicensePlate());
        }
        Vehicle newVehicle = vehicleDto.toEntity(userId);

        return vehicleRepository.save(newVehicle);
    }

    public Vehicle updateWithAccessCheck(UUID userId,UUID vehicleId, UpdateVehicleDTO updateVehicleDTO){

        Vehicle existingVehicle = vehicleRepository.findByIdAndOwnerId(vehicleId,userId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, userId + " does not own a vehicle with ID: " + vehicleId));

        if( updateVehicleDTO.getMake() != null){
            existingVehicle.setMake(updateVehicleDTO.getMake());
        }

        if( updateVehicleDTO.getModel() != null){
            existingVehicle.setModel(updateVehicleDTO.getModel());
        }

        if( updateVehicleDTO.getType() != null){
            existingVehicle.setType(VehicleType.valueOf(updateVehicleDTO.getType()));
        }

        if( updateVehicleDTO.getLicensePlate() != null){
            existingVehicle.setLicensePlate(updateVehicleDTO.getLicensePlate());
        }

        return vehicleRepository.save(existingVehicle);
    }


    public UUID deleteWithAuthCheck(UUID userId,UUID vehicleId){
        checkAccess(vehicleId,userId);
        vehicleRepository.deleteById(vehicleId);
        return vehicleId;
    }

    public Vehicle findOneWithAccessCheck(UUID userId, UUID vehicleId){
        checkAccess(vehicleId,userId);
        return vehicleRepository.findById(vehicleId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Vehicle with id: " + vehicleId + " not found"));
     }

    public List<Vehicle> findAllByUserId(UUID userId){
        return vehicleRepository.findAllByOwnerId(userId);
    }

    public void checkAccess(UUID vehicleID , UUID ownerId){
        if(!vehicleRepository.existsByIdAndOwnerId(vehicleID,ownerId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access Denied");
        }
    }
}
