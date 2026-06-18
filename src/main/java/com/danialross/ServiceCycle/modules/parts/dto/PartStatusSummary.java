package com.danialross.ServiceCycle.modules.parts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PartStatusSummary {
    private PartResponse part;
    private Integer monthsRemaining;
    private Integer monthsOverdue;
    private Integer kmsRemaining;
    private Integer kmsOverdue;
}
