package com.danialross.ServiceCycle.modules.maintenanceRecord;

import com.danialross.ServiceCycle.modules.maintenanceRecord.dto.CreateMaintenanceDTO;
import com.danialross.ServiceCycle.modules.mileageRecord.MileageRecordService;
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
        Vehicle vehicle = vehicleService.findOneWithAccessCheck(userId,createMaintenanceDTO.getVehicleId());

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

    public MaintenanceRecord findOneWithAccessCheck(UUID userId, UUID maintenanceId){
        MaintenanceRecord record = maintenanceRecordRepository.findById(maintenanceId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Maintenance with id: " + maintenanceId + "does not exist"));
        checkAccess(userId,record);
        return record;
    }

    public List<MaintenanceRecord> findAllWithAccessCheck(UUID userId, UUID vehicleId){
        List<MaintenanceRecord> records = maintenanceRecordRepository.findByVehicleId(vehicleId);
        checkAccess(userId,records.getFirst());
        return records;
    }

    public void checkAccess(UUID userId,MaintenanceRecord record){
        if(!record.getVehicle().getOwnerId().toString().equals(userId.toString()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User : " + userId + " cannot retrieve record");
    }
}
