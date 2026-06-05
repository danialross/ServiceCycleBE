package com.danialross.ServiceCycle.modules.parts;

import com.danialross.ServiceCycle.modules.maintenanceRecord.MaintenanceRecord;
import com.danialross.ServiceCycle.modules.parts.enums.PartPosition;
import com.danialross.ServiceCycle.modules.parts.enums.PartType;
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
                constraint = "lifespan_months IS NOT NULL OR lifespan_kms IS NOT NULL"
        )
)
@Schema(description = "Vehicle's Part entity")
public class Part {
    @Id
    @GeneratedValue()
    private UUID id;

    @Column()
    private String brand;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PartType type;

    @Column()
    @Enumerated(EnumType.STRING)
    private PartPosition position;

    @Column()
    private BigDecimal price;

    @Column(nullable = false)
    @Schema(description = "Part's life span in kilometers")
    private Integer lifespanKms;

    @Column(nullable = false)
    @Schema(description = "Part's life span in months")
    private Integer lifespanMonths;

    @ManyToOne
    @JoinColumn(name = "maintenance_record_id",nullable = false)
    private MaintenanceRecord maintenanceRecord;

    @Column()
    @Schema(description = "Part's index")
    private Integer index;
}
