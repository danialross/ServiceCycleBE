package com.danialross.ServiceCycle.modules.parts.dto;

import com.danialross.ServiceCycle.modules.parts.Part;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class PartsStatus {
    private List<Part> expiredParts;
    private List<ExpiringPart> expiringParts;
    private List<Part> goodParts;
}

