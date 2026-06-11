package com.danialross.ServiceCycle.modules.parts;

import com.danialross.ServiceCycle.modules.parts.dto.PartWithInstalledMileage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PartRepository extends JpaRepository<Part, UUID> {
    @Query("""
            select p as part, mr.mileage as installMileage, mr.date as installDate
            from MaintenanceRecord mr
            join mr.parts p
            where mr.vehicle.id = :vehicleId
            and
            mr.vehicle.ownerId = :ownerId
            and
            p.isActive = true
            """)
    List<PartWithInstalledMileage> findActiveParts(@Param("vehicleId") UUID vehicleId, @Param("ownerId") UUID ownerId);
}
