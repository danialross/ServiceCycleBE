package com.danialross.ServiceCycle.modules.MaintenanceRecord;

import com.danialross.ServiceCycle.modules.MaintenanceRecord.dto.CreateMaintenanceDTO;
import com.danialross.ServiceCycle.modules.parts.Part;
import com.danialross.ServiceCycle.modules.parts.dto.CreatePartDTO;
import com.danialross.ServiceCycle.modules.vehicles.Vehicle;
import com.danialross.ServiceCycle.modules.vehicles.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MaintenanceRecordService {
    private final MaintenanceRecordRepository maintenanceRecordRepository;
    private final VehicleService vehicleService;

    public MaintenanceRecord add(UUID ownerId, CreateMaintenanceDTO dto){
        vehicleService.checkVehicleWithOwnerExist(dto.getVehicleId(),ownerId);
        Vehicle vehicle = vehicleService.findOne(dto.getVehicleId());
        List<Part> parts = dto.getParts().stream().map(CreatePartDTO::toEntity).toList();
        MaintenanceRecord newRecord = dto.toRecord(vehicle,parts);
        return maintenanceRecordRepository.save(newRecord);
    }
}
