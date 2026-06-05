package com.danialross.ServiceCycle.modules.maintenanceRecord.dto;

import com.danialross.ServiceCycle.modules.maintenanceRecord.MaintenanceRecord;
import com.danialross.ServiceCycle.modules.parts.dto.PartResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Data
public class MaintenanceResponse {
    @Schema(description = "Maintenance ID")
    private UUID maintenanceId;
    @Schema(description = "Vehicle ID")
    private UUID vehicleId;
    @Schema(description = "Mileage of the vehicle being maintained")
    private Integer vehicleMileage;
    @Schema(description = "Date of maintenance")
    private LocalDate date;
    @Schema(description = "Part installed during maintenance")
    private List<PartResponse> parts;
    @Schema(description = "remarks")
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
