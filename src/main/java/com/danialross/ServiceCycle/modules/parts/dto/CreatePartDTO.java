package com.danialross.ServiceCycle.modules.parts.dto;

import com.danialross.ServiceCycle.modules.parts.Part;
import com.danialross.ServiceCycle.modules.parts.enums.PartPosition;
import com.danialross.ServiceCycle.modules.parts.enums.PartType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreatePartDTO {

    @NotNull(message = "Part type is required")
    @Pattern(regexp = PartType.regexPattern,
            message = "Part Type must one of the following : "+PartType.allValues)
    @Schema(description = "The type of the part")
    private String type;

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

    @Schema(description = "The position of the part")
    @Pattern(regexp = PartPosition.regexPattern,
            message = "Part position must one of the following : " +
                    PartPosition.allValues)
    private String position;

    @Schema(description = "Index of the part")
    @Positive(message = "Index must be 1 or more")
    private Integer index;

    public Part toEntity(){
        return Part.builder()
                .type(this.getType() != null ? PartType.valueOf(this.getType()): null)
                .position(this.getPosition() != null ? PartPosition.valueOf(this.getPosition()): null)
                .index(this.getIndex())
                .price(this.getPrice())
                .lifespanKms(this.getLifespanKms())
                .lifespanMonths(this.getLifespanMonths())
                .brand(this.getBrand())
                .build();

    }
}
