package com.danialross.ServiceCycle.vehicles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle,UUID> {
    boolean existsByOwnerIdAndLicensePlate(UUID ownerId,String licensePlate);
    Optional<Vehicle> findByIdAndOwnerId( UUID vehicleId,UUID ownerId);
}
