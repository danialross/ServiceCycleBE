package com.danialross.ServiceCycle.modules.parts.dto;

import com.danialross.ServiceCycle.modules.parts.Part;
import com.danialross.ServiceCycle.modules.parts.enums.PartPosition;
import com.danialross.ServiceCycle.modules.parts.enums.PartType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Data
public class PartResponse {
    @Schema(description = "Vehicle part ID")
    private UUID id;
    @Schema(description = "Brand of the vehicle part")
    private String brand;
    @Schema(description = "Type of vehicle part")
    private PartType type;
    @Schema(description = "Price of vehicle part")
    private BigDecimal price;
    @Schema(description = "How long the part will last in Kilometers")
    private Integer lifespanKms;
    @Schema(description = "How long the part will last in months")
    private Integer lifespanMonths;
    @Schema(description = "Where the part is installed on the vehicle")
    private PartPosition position;
    @Schema(description = "Numbering for parts with multiples")
    private Integer index;
    @Schema(description = "Maintenance record ID")
    private UUID maintenanceId;

    public static PartResponse fromPart(Part part){
        return PartResponse.builder()
                .id(part.getId())
                .brand(part.getBrand())
                .type(part.getType())
                .price(part.getPrice())
                .lifespanKms(part.getLifespanKms())
                .lifespanMonths(part.getLifespanMonths())
                .position(part.getPosition())
                .index(part.getIndex())
                .maintenanceId(part.getMaintenanceRecord().getId())
                .build();
    }
}
