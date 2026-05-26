package com.danialross.ServiceCycle.modules.MaintenanceRecord;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MaintenanceRecordRepository extends JpaRepository<MaintenanceRecord, UUID> {
}
