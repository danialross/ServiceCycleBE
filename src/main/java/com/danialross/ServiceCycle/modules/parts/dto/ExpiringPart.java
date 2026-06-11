package com.danialross.ServiceCycle.modules.parts.dto;

import com.danialross.ServiceCycle.modules.parts.Part;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ExpiringPart {
    Part part;
    Integer expiringInKm;
    Integer expiringInMonths;
}
