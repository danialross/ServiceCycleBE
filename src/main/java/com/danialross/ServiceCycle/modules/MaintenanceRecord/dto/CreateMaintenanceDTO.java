package com.danialross.ServiceCycle.modules.MaintenanceRecord.dto;

import com.danialross.ServiceCycle.modules.MaintenanceRecord.MaintenanceRecord;
import com.danialross.ServiceCycle.modules.MileageRecord.dto.CreateMileageRecordDTO;
import com.danialross.ServiceCycle.modules.parts.dto.CreatePartDTO;
import com.danialross.ServiceCycle.modules.vehicles.Vehicle;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class CreateMaintenanceDTO {

    @Schema(description = "Vehicle ID")
    @NotNull(message = "Vehicle id is required")
    private UUID vehicleId;

    @Schema(description = "Mileage of the vehicle when the maintenance was done")
    @PositiveOrZero(message = "Mileage but be 0 or more")
    @NotNull(message = "Mileage is required")
    private Integer vehicleMileage;

    @Schema(description = "Date of when the maintenance was done")
    @NotNull(message = "Date of maintenance is required")
    @PastOrPresent(message = "Maintenance must be now or previously")
    private LocalDate date;

    @Schema(description = "Parts that was added for the maintenance")
    @Valid
    @NotEmpty(message = "Maintenance must contain parts")
    private List<CreatePartDTO> parts;

    private String description;

    public MaintenanceRecord toRecord(Vehicle vehicle){
        return MaintenanceRecord.builder()
                .date(date)
                .description(description)
                .vehicle(vehicle)
                .vehicleMileage(vehicleMileage)
                .build();
    }

    public CreateMileageRecordDTO toMileageRecordDTO(Vehicle vehicle){
        return CreateMileageRecordDTO.builder()
                .mileage(vehicleMileage)
                .date(date)
                .vehicleId(vehicleId)
                .build();
    }
}
