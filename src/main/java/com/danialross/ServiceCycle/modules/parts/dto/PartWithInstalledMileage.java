package com.danialross.ServiceCycle.modules.parts.dto;

import com.danialross.ServiceCycle.modules.parts.Part;

import java.time.LocalDate;

public interface PartWithInstalledMileage {
    Integer getInstallMileage();
    LocalDate getInstallDate();
    Part getPart();
}
