package com.danialross.ServiceCycle.modules.MaintenanceRecord.dto;

import com.danialross.ServiceCycle.modules.MaintenanceRecord.MaintenanceRecord;
import com.danialross.ServiceCycle.modules.parts.dto.PartResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Builder
public class MaintenanceResponse {
    private UUID maintenanceId;
    private UUID vehicleId;
    private Integer vehicleMileage;
    private LocalDate date;
    private List<PartResponse> parts;
    private String description;

    public static MaintenanceResponse fromMaintenance(MaintenanceRecord record){
        List<PartResponse> parts = record.getParts().stream().map(PartResponse::fromPart).toList();
        return MaintenanceResponse.builder()
                .maintenanceId(record.getId())
                .vehicleId(record.getVehicle().getId())
                .vehicleMileage(record.getVehicleMileage())
                .date(record.getDate())
                .parts(parts)
                .description(record.getDescription())
                .build();
    }
}
