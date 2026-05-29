package com.danialross.ServiceCycle.modules.MileageRecord;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MileageRecordRepository extends JpaRepository<MileageRecord, UUID> {
    Optional<MileageRecord> findByVehicleId(UUID vehicleId);
    Optional<MileageRecord> findTopByVehicleIdOrderByDateDesc(UUID vehicleId);
}
