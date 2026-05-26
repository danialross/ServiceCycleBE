package com.danialross.ServiceCycle.modules.parts.dto;

import com.danialross.ServiceCycle.modules.parts.Part;
import com.danialross.ServiceCycle.modules.parts.PartType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreatePartDTO {

    @NotNull(message = "Part type is required")
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

    public Part toEntity(){
        return Part.builder()
                .type(this.getType())
                .price(this.getPrice())
                .lifespanKms(this.getLifespanKms())
                .lifespanMonths(this.getLifespanMonths())
                .brand(this.getBrand())
                .build();

    }
}
