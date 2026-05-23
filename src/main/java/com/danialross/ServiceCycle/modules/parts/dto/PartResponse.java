package com.danialross.ServiceCycle.modules.parts.dto;

import com.danialross.ServiceCycle.modules.parts.Part;
import com.danialross.ServiceCycle.modules.parts.PartType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class PartResponse {
    private UUID id;
    private UUID vehicleId;
    private String brand;
    private PartType type;
    private BigDecimal price;
    private Integer validityDistance;
    private Integer validityTime;

    public static PartResponse fromPart(Part part){
        return PartResponse.builder()
                .id(part.getId())
                .vehicleId(part.getVehicle().getId())
                .brand(part.getBrand())
                .type(part.getType())
                .price(part.getPrice())
                .validityDistance(part.getValidityDistance())
                .validityTime(part.getValidityTime())
                .build();
    }
}
