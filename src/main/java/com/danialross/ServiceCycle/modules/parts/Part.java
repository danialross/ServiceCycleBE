package com.danialross.ServiceCycle.modules.parts;

import com.danialross.ServiceCycle.modules.vehicles.Vehicle;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Table(
        name = "parts",
        check = @CheckConstraint(
                name = "check_replacement_required",
                constraint = "lifespan_months IS NOT NULL OR lifespan_km IS NOT NULL"
        )
)
@Schema(description = "Vehicle's Part entity")
public class Part {
    @Id
    @GeneratedValue()
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "vehicle_id",nullable = false)
    private Vehicle vehicle;

    @Column()
    private String brand;

    @Column()
    @Enumerated(EnumType.STRING)
    private PartType type;

    @Column()
    private BigDecimal price;

    @Column()
    @Schema(description = "Part's life span in kilometers")
    private Integer lifespanKm;

    @Column()
    @Schema(description = "Part's life span in months")
    private Integer lifespanMonths;
}
