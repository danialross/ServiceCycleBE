package com.danialross.ServiceCycle.modules.MileageRecord;

import com.danialross.ServiceCycle.modules.MileageRecord.dto.CreateMileageRecordDTO;
import com.danialross.ServiceCycle.modules.vehicles.Vehicle;
import com.danialross.ServiceCycle.modules.vehicles.VehicleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MileageRecordService {
    private final MileageRecordRepository mileageRecordRepository;
    private final VehicleService vehicleService;

    @Transactional
    public MileageRecord add(UUID userId, CreateMileageRecordDTO mileageDto){
        vehicleService.checkVehicleWithOwnerExist(mileageDto.getVehicleId(),userId);
        mileageRecordRepository.findTopByVehicleIdOrderByDateDesc(mileageDto.getVehicleId())
                .ifPresent(previousRecord -> {
                    if (mileageDto.getMileage() < previousRecord.getMileage()) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                "Mileage cannot be less than most recent entry of " + previousRecord.getMileage() + "km");
                    }
        });
        Vehicle vehicle = vehicleService.findOne(mileageDto.getVehicleId());
        MileageRecord newRecord = mileageDto.toRecord(vehicle);
        return mileageRecordRepository.save(newRecord);
    }



    public MileageRecord findByVehicleId(UUID vehicleId){
        return mileageRecordRepository.findByVehicleId(vehicleId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Mileage record for vehicle id: " + vehicleId + " not found"));
    }

}
