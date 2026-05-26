package com.danialross.ServiceCycle.modules.parts;

import com.danialross.ServiceCycle.modules.vehicles.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/part")
@RequiredArgsConstructor
public class PartController {
    private final PartService partService;
    private final VehicleService vehicleService;
}
