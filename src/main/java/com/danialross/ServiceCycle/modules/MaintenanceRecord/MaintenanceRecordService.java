package com.danialross.ServiceCycle.modules.MaintenanceRecord;

import com.danialross.ServiceCycle.modules.MaintenanceRecord.dto.CreateMaintenanceDTO;
import com.danialross.ServiceCycle.modules.MileageRecord.MileageRecord;
import com.danialross.ServiceCycle.modules.MileageRecord.MileageRecordService;
import com.danialross.ServiceCycle.modules.MileageRecord.dto.CreateMileageRecordDTO;
import com.danialross.ServiceCycle.modules.parts.Part;
import com.danialross.ServiceCycle.modules.parts.PartService;
import com.danialross.ServiceCycle.modules.parts.dto.CreatePartDTO;
import com.danialross.ServiceCycle.modules.vehicles.Vehicle;
import com.danialross.ServiceCycle.modules.vehicles.VehicleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MaintenanceRecordService {
    private final MaintenanceRecordRepository maintenanceRecordRepository;
    private final VehicleService vehicleService;
    private final PartService partService;
    private final MileageRecordService mileageRecordService;

    @Transactional
    public MaintenanceRecord add(UUID userId, CreateMaintenanceDTO createMaintenanceDTO){
        vehicleService.checkVehicleWithOwnerExist(createMaintenanceDTO.getVehicleId(),userId);
        Vehicle vehicle = vehicleService.findOne(createMaintenanceDTO.getVehicleId());

        for(CreatePartDTO part : createMaintenanceDTO.getParts()){
            partService.validatePart(part);
        }

        MaintenanceRecord newRecord = createMaintenanceDTO.toRecord(vehicle);

        List<Part> parts = new ArrayList<>();
        for(CreatePartDTO createPartDTO : createMaintenanceDTO.getParts()){
            Part part = createPartDTO.toEntity();
            part.setMaintenanceRecord(newRecord);
            parts.add(part);
        }
        newRecord.setParts(parts);
        mileageRecordService.add(userId,createMaintenanceDTO.toMileageRecordDTO(vehicle));

        return maintenanceRecordRepository.save(newRecord);
    }

    public MaintenanceRecord findOne(UUID maintenanceId){
        return maintenanceRecordRepository.findById(maintenanceId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Maintenance with id: " + maintenanceId + "does not exist"));
    }

    public MaintenanceRecord getByOwner(UUID ownerId, UUID maintenanceId){

        MaintenanceRecord record = findOne(maintenanceId);

        if(!record.getVehicle().getOwnerId().toString().equals(ownerId.toString()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Owner : " + ownerId + " cannot retrieve record");

        return record;
    }
}
