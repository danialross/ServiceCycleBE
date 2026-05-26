package com.danialross.ServiceCycle.modules.parts.dto;

import com.danialross.ServiceCycle.modules.parts.PartType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class UpdatePartDTO {

    @NotNull(message = "Part Id cannot be null")
    UUID partId;

    @NotNull(message = "Vehicle Id cannot be null")
    UUID vehicleId;

    @Schema(description = "The type of the part")
    @Pattern(regexp = "TYRES|CHAIN|SPROCKET|ENGINE_OIL|BRAKE_FLUID|COOLANT|BRAKE_PADS|VALVE_CLEARANCE|AIR_FILTER|CLUTCH|BATTERY|SPARK_PLUG|TRANSMISSION_FLUID|OTHERS",
    message = "Part type must one of the follow : [TYRES,CHAIN,SPROCKET,ENGINE_OIL,BRAKE_FLUID,COOLANT,BRAKE_PADS,VALVE_CLEARANCE,AIR_FILTER,CLUTCH,BATTERY,SPARK_PLUG,TRANSMISSION_FLUID,OTHERS]")
    private PartType type;

    @PositiveOrZero(message = "Must be more than 0KM")
    @Schema(description = "numeric value in kilometers")
    private Integer lifespanKms;

    @PositiveOrZero(message = "Must be more than 0 months")
    @Schema(description = "numeric value in months")
    private Integer lifespanMonths;

    @PositiveOrZero(message = "Must be more than $0")
    private BigDecimal price;

    @Schema(description = "The brand of the part")
    private String brand;

    @Schema(description = "parts description")
    private String description;

}
