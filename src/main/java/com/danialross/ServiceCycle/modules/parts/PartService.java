package com.danialross.ServiceCycle.modules.parts;

import com.danialross.ServiceCycle.modules.MaintenanceRecord.MaintenanceRecord;
import com.danialross.ServiceCycle.modules.parts.dto.CreatePartDTO;
import com.danialross.ServiceCycle.modules.parts.dto.UpdatePartDTO;
import com.danialross.ServiceCycle.modules.vehicles.Vehicle;
import com.danialross.ServiceCycle.modules.vehicles.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PartService {
    private final PartRepository partRepository;
    private final VehicleService vehicleService;

    public Part update(UUID ownerId, UpdatePartDTO partDTO) {
        Part part = partRepository.findById(partDTO.getPartId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Part with id: " + partDTO.getPartId() + " not found."));
        return new Part();
    }
}
