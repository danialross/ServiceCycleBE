package com.danialross.ServiceCycle.modules.maintenanceRecord.dto;

import com.danialross.ServiceCycle.modules.parts.dto.UpdatePartDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class UpdateMaintenanceDTO {

    @Schema(description = "Maintenance ID")
    @NotNull(message = "Maintenance id is required")
    private UUID maintenanceId;

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

    @Schema(description = "Parts that was updated for the maintenance")
    @Valid
    @NotEmpty(message = "Maintenance must contain parts")
    private List<UpdatePartDTO> parts;

    private String description;
}
