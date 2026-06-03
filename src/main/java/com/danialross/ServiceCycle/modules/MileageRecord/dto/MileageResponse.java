package com.danialross.ServiceCycle.modules.MileageRecord.dto;

import com.danialross.ServiceCycle.modules.MileageRecord.MileageRecord;
import com.danialross.ServiceCycle.modules.vehicles.Vehicle;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class MileageResponse {

    @Schema(description = "UUID of the mileage record")
    private UUID id;

    @Schema(description = "mileage of vehicle when record was")
    private Integer mileage;

    @Schema(description = "date when record was")
    private LocalDate date;

    @Schema(description = "Vehicle of the record")
    private UUID vehicleId;

    public static MileageResponse fromRecord(MileageRecord record){
        return MileageResponse.builder()
                .id(record.getId())
                .mileage(record.getMileage())
                .date(record.getDate())
                .vehicleId(record.getVehicle().getId())
                .build();
    }
}
