package com.danialross.ServiceCycle.modules.vehicles;

import com.danialross.ServiceCycle.modules.MileageRecord.MileageRecord;
import com.danialross.ServiceCycle.modules.vehicles.dto.CreateVehicleDTO;
import com.danialross.ServiceCycle.modules.vehicles.dto.UpdateVehicleDTO;
import com.danialross.ServiceCycle.modules.vehicles.dto.VehicleQueryDTO;
import com.danialross.ServiceCycle.modules.vehicles.enums.VehicleType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
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

    public Vehicle update(UUID userId, UpdateVehicleDTO updateVehicleDTO){
        UUID vehicleId = UUID.fromString(updateVehicleDTO.getId());

        Vehicle existingVehicle = vehicleRepository.findByIdAndOwnerId(vehicleId,userId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, ownerId + " does not own a vehicle with ID: " + updateVehicleDTO.getId()));

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


    public UUID delete(UUID userId,UUID vehicleId){
        if(!vehicleRepository.existsByIdAndOwnerId(vehicleId,userId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND,ownerId + " cannot delete vehicle with id: " + vehicleId);
        vehicleRepository.deleteById(vehicleId);
        return vehicleId;
    }

    public Vehicle findOne(UUID id){
        return vehicleRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Vehicle with id: " + id + " not found"));
     }

    public List<Vehicle> findAll(UUID userId, VehicleQueryDTO queries){

        Specification<Vehicle> spec = Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("ownerId"),userId));

        if(queries.getMake() != null){
            spec = spec.and(((root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("make")), '%'+queries.getMake().toLowerCase()+'%')));
        }

        if(queries.getModel() != null){
            spec = spec.and(((root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("model")),'%'+queries.getModel().toLowerCase()+'%')));
        }

        if(queries.getLicensePlate() != null){
            spec = spec.and(((root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("licensePlate")),'%'+queries.getLicensePlate().toLowerCase()+'%')));
        }

        if(queries.getType() != null){
            spec = spec.and((root,query,criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("type")),'%'+queries.getType().toLowerCase()+'%'));
        }

        if(queries.getMinMileage() != null){
            spec = spec.and((root,query,criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("mileage"),queries.getMinMileage()));
        }

        if(queries.getMaxMileage() != null){
            spec = spec.and((root,query,criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("mileage"),queries.getMaxMileage()));
        }

        return vehicleRepository.findAll(spec);
    }

    public void checkVehicleWithOwnerExist(UUID vehicleID , UUID ownerId){
        if(!vehicleRepository.existsByIdAndOwnerId(vehicleID,ownerId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access Denied");
        }
    }
}
