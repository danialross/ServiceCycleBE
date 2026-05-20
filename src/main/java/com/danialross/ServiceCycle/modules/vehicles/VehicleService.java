package com.danialross.ServiceCycle.modules.vehicles;

import com.danialross.ServiceCycle.modules.vehicles.dto.CreateVehicleDTO;
import com.danialross.ServiceCycle.modules.vehicles.dto.UpdateVehicleDTO;
import com.danialross.ServiceCycle.modules.vehicles.dto.VehicleQueryDTO;
import com.danialross.ServiceCycle.modules.vehicles.dto.VehicleResponse;
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

    public VehicleResponse update(UUID ownerId, UpdateVehicleDTO updateVehicleDTO){
        UUID vehicleId = UUID.fromString(updateVehicleDTO.getId());

        Vehicle existingVehicle = vehicleRepository.findByIdAndOwnerId(vehicleId,ownerId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, ownerId + " does not own a vehicle with ID: " + updateVehicleDTO.getId()));

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

        if( updateVehicleDTO.getMileage() != null){
            if(updateVehicleDTO.getMileage() < existingVehicle.getMileage()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Mileage cannot be lower than before");
            existingVehicle.setMileage(updateVehicleDTO.getMileage());
        }

        Vehicle savedVehicle = vehicleRepository.save(existingVehicle);

        return VehicleResponse.fromVehicle(savedVehicle);
    }


    public VehicleResponse delete(UUID ownerId,UUID vehicleId){
        if(!vehicleRepository.existsByIdAndOwnerId(vehicleId,ownerId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND,ownerId + " cannot delete vehicle with id: " + vehicleId);
        vehicleRepository.deleteById(vehicleId);
        return VehicleResponse.builder().id(vehicleId)
                .build();
    }

    public VehicleResponse findOne(UUID ownerId, UUID id){
        Vehicle vehicle = vehicleRepository.findByIdAndOwnerId(ownerId,id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Vehicle with id: " + id + " not found"));
        return VehicleResponse.fromVehicle(vehicle);
    }

    public List<VehicleResponse> findAll(UUID ownerId, VehicleQueryDTO queries){

        Specification<Vehicle> spec = Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("ownerId"),ownerId));

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

        List<Vehicle> result = vehicleRepository.findAll(spec);

        return result.stream().map(VehicleResponse::fromVehicle).toList();
    }
}
