package com.danialross.ServiceCycle.modules.MileageRecord;

import com.danialross.ServiceCycle.modules.MileageRecord.dto.CreateMileageRecordDTO;
import com.danialross.ServiceCycle.modules.MileageRecord.dto.UpdateMileageRecordDTO;
import com.danialross.ServiceCycle.modules.vehicles.Vehicle;
import com.danialross.ServiceCycle.modules.vehicles.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MileageRecordService {
    private final MileageRecordRepository mileageRecordRepository;
    private final VehicleService vehicleService;

    public MileageRecord add(UUID userId, CreateMileageRecordDTO mileageDto){
        vehicleService.checkVehicleWithOwnerExist(mileageDto.getVehicleId(),userId);

        MileageRecord entryBeforeDtoDate = mileageRecordRepository.findEntryBefore(mileageDto.getVehicleId(), LocalDate.now(),mileageDto.getMileage(),null).orElse(null);

        if(entryBeforeDtoDate != null && mileageDto.getMileage() < entryBeforeDtoDate.getMileage() ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Mileage cannot be less than entry before it of " + entryBeforeDtoDate.getMileage() + "km");
        }

        Vehicle vehicle = vehicleService.findOne(mileageDto.getVehicleId());
        MileageRecord newRecord = mileageDto.toRecord(vehicle);
        newRecord.setDate(LocalDate.now());
        return mileageRecordRepository.save(newRecord);
    }

    public MileageRecord update(UUID userId,UUID mileageRecordId, UpdateMileageRecordDTO mileageDto){
        vehicleService.checkVehicleWithOwnerExist(mileageDto.getVehicleId(),userId);

        MileageRecord entryBeforeDtoDate = mileageRecordRepository.findEntryBefore(mileageDto.getVehicleId(), mileageDto.getDate(),mileageDto.getMileage(),mileageRecordId).orElse(null);
        MileageRecord entryAfterDtoDate = mileageRecordRepository.findEntryAfter(mileageDto.getVehicleId(), mileageDto.getDate(),mileageDto.getMileage(),mileageRecordId).orElse(null);

        StringBuilder error = new StringBuilder();

        if(entryBeforeDtoDate != null && mileageDto.getMileage() < entryBeforeDtoDate.getMileage() ) {
            error.append("Mileage cannot be less than entry before it of " + entryBeforeDtoDate.getMileage() + "km\n");
        }

        if(entryAfterDtoDate != null && mileageDto.getMileage() > entryAfterDtoDate.getMileage() ) {
            error.append("Mileage cannot be more than entry after it of " + entryAfterDtoDate.getMileage() + "km\n");
        }

        if(!error.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error.toString());
        }

        Vehicle vehicle = vehicleService.findOne(mileageDto.getVehicleId());
        MileageRecord newRecord = mileageDto.toRecord(vehicle);
        newRecord.setId(mileageRecordId);
        return mileageRecordRepository.save(newRecord);
    }

    public UUID delete(UUID userId, UUID mileageRecordId){
        MileageRecord record = mileageRecordRepository.findById(mileageRecordId).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Record does not exists"));

        if(!record.getVehicle().getOwnerId().equals(userId)) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"User cannot delete record");

        mileageRecordRepository.delete(record);

        return mileageRecordId;
    }

    public MileageRecord get(UUID vehicleId){
        return mileageRecordRepository.findTopByVehicleIdOrderByDateDescMileageDesc(vehicleId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Vehicle: " + vehicleId + " not found"));
    }

}
