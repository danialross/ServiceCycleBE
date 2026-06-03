package com.danialross.ServiceCycle.modules.MileageRecord.dto;

import com.danialross.ServiceCycle.modules.MileageRecord.MileageRecord;
import com.danialross.ServiceCycle.modules.vehicles.Vehicle;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class CreateMileageRecordDTO {
    @Schema(description = "New mileage entry for vehicle")
    private Integer mileage;
    @Schema(description = "Vehicle ID of the new mileage record")
    private UUID vehicleId;

    public MileageRecord toRecord(Vehicle vehicle){
        return MileageRecord.builder()
                .mileage(mileage)
                .vehicle(vehicle)
                .build();
    }
}
