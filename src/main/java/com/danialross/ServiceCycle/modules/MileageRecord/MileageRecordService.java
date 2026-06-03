package com.danialross.ServiceCycle.modules.MileageRecord;

import com.danialross.ServiceCycle.modules.MileageRecord.dto.CreateMileageRecordDTO;
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

        MileageRecord entryBeforeDtoDate = mileageRecordRepository.findTopByVehicleIdAndDateBeforeOrderByDateDesc(mileageDto.getVehicleId(), LocalDate.now()).orElse(null);

        if(entryBeforeDtoDate != null && mileageDto.getMileage() < entryBeforeDtoDate.getMileage() ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Mileage cannot be less than entry before it of " + entryBeforeDtoDate.getMileage() + "km");
        }

        Vehicle vehicle = vehicleService.findOne(mileageDto.getVehicleId());
        MileageRecord newRecord = mileageDto.toRecord(vehicle);
        newRecord.setDate(LocalDate.now());
        return mileageRecordRepository.save(newRecord);
    }

}
