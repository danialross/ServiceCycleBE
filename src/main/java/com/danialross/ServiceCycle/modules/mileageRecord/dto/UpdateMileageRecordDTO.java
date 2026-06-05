package com.danialross.ServiceCycle.modules.mileageRecord.dto;

import com.danialross.ServiceCycle.modules.mileageRecord.MileageRecord;
import com.danialross.ServiceCycle.modules.vehicles.Vehicle;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class UpdateMileageRecordDTO {
    @Schema(description = "Mileage entry for vehicle")
    private Integer mileage;
    @Schema(description = "Vehicle ID of the mileage record")
    private UUID vehicleId;
    @Schema(description = "Date of mileage record")
    private LocalDate date;


    public MileageRecord toRecord(Vehicle vehicle){
        return MileageRecord.builder()
                .mileage(mileage)
                .vehicle(vehicle)
                .date(date)
                .build();
    }
}

