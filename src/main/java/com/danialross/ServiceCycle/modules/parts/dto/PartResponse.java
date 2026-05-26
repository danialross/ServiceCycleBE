package com.danialross.ServiceCycle.modules.parts.dto;

import com.danialross.ServiceCycle.modules.parts.Part;
import com.danialross.ServiceCycle.modules.parts.PartType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@Builder
public class PartResponse {
    private UUID id;
    private UUID vehicleId;
    private String brand;
    private PartType type;
    private BigDecimal price;
    private Integer lifespanKms;
    private Integer lifespanMonths;

    public static PartResponse fromPart(Part part){
        return PartResponse.builder()
                .id(part.getId())
                .brand(part.getBrand())
                .type(part.getType())
                .price(part.getPrice())
                .lifespanKms(part.getLifespanKms())
                .lifespanMonths(part.getLifespanMonths())
                .build();
    }
}
