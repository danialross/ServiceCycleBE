package com.danialross.ServiceCycle.modules.vehicles;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VehicleRepository extends JpaRepository<Vehicle,UUID> {
    boolean existsByOwnerIdAndLicensePlate(UUID ownerId,String licensePlate);
    boolean existsByIdAndOwnerId(UUID id, UUID ownerId);
    Optional<Vehicle> findByIdAndOwnerId( UUID vehicleId,UUID ownerId);
    List<Vehicle> findAll(Specification<Vehicle> spec);
}
