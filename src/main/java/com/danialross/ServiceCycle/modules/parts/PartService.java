package com.danialross.ServiceCycle.modules.parts;

import com.danialross.ServiceCycle.modules.parts.dto.CreatePartDTO;
import com.danialross.ServiceCycle.modules.vehicles.Vehicle;
import com.danialross.ServiceCycle.modules.vehicles.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PartService {
    private final PartRepository partRepository;
    private final VehicleService vehicleService;

    public Part register(UUID ownerId, CreatePartDTO partDTO) {
        Vehicle vehicle = vehicleService.findOne(ownerId,partDTO.getVehicleId());
        Part newPart = partDTO.toEntity();
        newPart.setVehicle(vehicle);
        return partRepository.save(newPart);
    }
}
