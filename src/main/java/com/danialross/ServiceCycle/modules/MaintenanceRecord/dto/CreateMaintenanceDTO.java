package com.danialross.ServiceCycle.modules.MaintenanceRecord.dto;

import com.danialross.ServiceCycle.modules.MaintenanceRecord.MaintenanceRecord;
import com.danialross.ServiceCycle.modules.parts.Part;
import com.danialross.ServiceCycle.modules.parts.dto.CreatePartDTO;
import com.danialross.ServiceCycle.modules.vehicles.Vehicle;
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

    @NotNull(message = "Vehicle id is required")
    private UUID vehicleId;

    @PositiveOrZero(message = "Mileage but be 0 or more")
    @NotNull(message = "Mileage is required")
    private Integer vehicleMileage;

    @NotNull(message = "Date of maintenance is required")
    @PastOrPresent(message = "Maintenance must be now or previously")
    private LocalDate date;

    @Valid
    @NotEmpty(message = "Maintenance must contain parts")
    private List<CreatePartDTO> parts;

    private String description;

    public MaintenanceRecord toRecord(Vehicle vehicle,List<Part> parts){
        return MaintenanceRecord.builder()
                .date(date)
                .description(description)
                .vehicle(vehicle)
                .parts(parts)
                .build();
    }
}
