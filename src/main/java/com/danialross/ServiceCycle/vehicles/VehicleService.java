package com.danialross.ServiceCycle.vehicles;

import com.danialross.ServiceCycle.vehicles.dto.CreateVehicleDTO;
import com.danialross.ServiceCycle.vehicles.dto.UpdateVehicleDTO;
import com.danialross.ServiceCycle.vehicles.dto.VehicleQueryDTO;
import com.danialross.ServiceCycle.vehicles.dto.VehicleResponse;
import com.danialross.ServiceCycle.vehicles.enums.VehicleType;
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

        List<Vehicle> result = vehicleRepository.findAll(spec);

        return result.stream().map(VehicleResponse::fromVehicle).toList();
    }
}
