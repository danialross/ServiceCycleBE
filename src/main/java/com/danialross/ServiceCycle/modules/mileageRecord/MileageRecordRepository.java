package com.danialross.ServiceCycle.modules.mileageRecord;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MileageRecordRepository extends JpaRepository<MileageRecord, UUID> {
    // entry just before in (date, mileage) order — excluding itself
    @Query("""
    SELECT m FROM MileageRecord m
    WHERE m.vehicle.id = :vehicleId
    AND m.id != :excludeId
    AND (m.date < :date OR (m.date = :date AND m.mileage < :mileage))
    ORDER BY m.date DESC, m.mileage DESC
    LIMIT 1
    """)
    Optional<MileageRecord> findEntryBefore(UUID vehicleId, LocalDate date, int mileage, UUID excludeId);

    // entry just after in (date, mileage) order — excluding itself
    @Query("""
    SELECT m FROM MileageRecord m
    WHERE m.vehicle.id = :vehicleId
    AND m.id != :excludeId
    AND (m.date > :date OR (m.date = :date AND m.mileage > :mileage))
    ORDER BY m.date ASC, m.mileage ASC
    LIMIT 1
    """)
    Optional<MileageRecord> findEntryAfter(UUID vehicleId, LocalDate date, int mileage, UUID excludeId);

    Optional<MileageRecord> findTopByVehicleIdOrderByDateDescMileageDesc(UUID vehicleId);
    List<MileageRecord> findByVehicleIdOrderByDateDesc(UUID vehicleId);
}

