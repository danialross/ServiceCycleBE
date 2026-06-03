package com.danialross.ServiceCycle.modules.MileageRecord;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MileageRecordRepository extends JpaRepository<MileageRecord, UUID> {
    Optional<MileageRecord> findByVehicleId(UUID vehicleId);
    Optional<MileageRecord> findTopByVehicleIdAndDateBeforeOrderByDateDesc(UUID vehicleId, LocalDate date);
    Optional<MileageRecord> findTopByVehicleIdAndDateAfterOrderByDateDesc(UUID vehicleId,LocalDate date);
    List<MileageRecord> findByVehicleIdOrderByDateDesc(UUID vehicleId);

}

