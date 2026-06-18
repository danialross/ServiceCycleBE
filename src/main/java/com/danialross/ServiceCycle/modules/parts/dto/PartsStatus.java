package com.danialross.ServiceCycle.modules.parts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class PartsStatus {
    private List<PartStatusSummary> expiredParts;
    private List<PartStatusSummary> expiringParts;
    private List<PartStatusSummary> goodParts;
}

