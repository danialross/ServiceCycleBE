package com.danialross.ServiceCycle.modules.parts;

import com.danialross.ServiceCycle.modules.mileageRecord.MileageRecord;
import com.danialross.ServiceCycle.modules.mileageRecord.MileageRecordService;
import com.danialross.ServiceCycle.modules.parts.dto.*;
import com.danialross.ServiceCycle.modules.parts.enums.PartType;
import com.danialross.ServiceCycle.modules.vehicles.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class PartService {
    private final PartRepository partRepository;
    private final VehicleService vehicleService;
    private final MileageRecordService mileageRecordService;

    public Part update(UUID ownerId, UpdatePartDTO partDTO) {
        return new Part();
    }

    public Part delete(UUID ownerId, UpdatePartDTO partDTO) {
        return new Part();
    }

    public Part create(CreatePartDTO partDTO) {
        return new Part();
    }

    public void validatePart(CreatePartDTO dto) {

        //parts with multiple must have position and index else position and index must be null
        boolean hasPositionedParts = PartType.positionedParts.contains(dto.getType());
        boolean hasIndexedParts = PartType.indexedParts.contains(dto.getType());

        boolean hasPosition = dto.getPosition() != null;
        boolean hasIndexes = dto.getIndex() != null;
        StringBuilder error = new StringBuilder();

        if (hasPositionedParts) {
            if (!hasPosition) error.append(PartType.positionedParts + " must have positions\n");
            if (hasIndexes) error.append(PartType.positionedParts + " cant have index\n");
        }

        if (hasIndexedParts) {
            if (!hasIndexes) error.append(PartType.indexedParts + " must have indexes\n");
            if (hasPosition) error.append(PartType.indexedParts + " cant have positions\n");
        }

        if(!(hasIndexedParts || hasPositionedParts) && (hasIndexes || hasPosition))
            error.append("Parts cannot have index or positions\n");

        if(!error.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error.toString());
        }
    }


    public PartsStatus getPartsStatus(UUID ownerId, UUID vehicleId){
        List<PartWithInstalledMileage> partsUsedByVehicle = partRepository.findActiveParts(vehicleId,ownerId);

        List<PartStatusSummary> expiringParts = new ArrayList<>();
        List<PartStatusSummary> expiredParts = new ArrayList<>();
        List<PartStatusSummary> goodParts = new ArrayList<>();

        MileageRecord vehicleMileage = mileageRecordService.getLatestMileageRecord(ownerId,vehicleId);
        System.out.println(partsUsedByVehicle);
        for( PartWithInstalledMileage part : partsUsedByVehicle){

            boolean nearingDuration = LocalDate.now().isAfter(part.getInstallDate().plusMonths(part.getPart().getLifespanMonths()-1));
            boolean nearingDistance = vehicleMileage.getMileage() >= part.getInstallMileage() + part.getPart().getLifespanKms() + 1000;

            boolean isPartMileageOverdue = vehicleMileage.getMileage() >= part.getInstallMileage() + part.getPart().getLifespanKms();
            boolean isPartDurationOverdue = LocalDate.now().isAfter(part.getInstallDate().plusMonths(part.getPart().getLifespanMonths()));

            PartStatusSummary.PartStatusSummaryBuilder summary = PartStatusSummary.builder().part(PartResponse.fromPart(part.getPart()));

            if(isPartMileageOverdue || isPartDurationOverdue ){

                if(isPartMileageOverdue){

                    summary.kmsOverdue(vehicleMileage.getMileage() - (part.getInstallMileage() + part.getPart().getLifespanKms()));

                }

                if(isPartDurationOverdue){
                    summary.monthsOverdue(
                            (int) ChronoUnit.MONTHS.between(
                                    part.getInstallDate().plusMonths(part.getPart().getLifespanMonths()),
                                    LocalDate.now()
                            ));
                }

                expiredParts.add(summary.build());

            }else{

                Integer expiringIn = part.getInstallMileage() + part.getPart().getLifespanKms() - vehicleMileage.getMileage();
                summary.kmsRemaining(expiringIn);

                LocalDate expiryDate = part.getInstallDate().plusMonths(part.getPart().getLifespanMonths());
                long monthsRemaining = ChronoUnit.MONTHS.between(LocalDate.now(), expiryDate);
                summary.monthsRemaining((int)monthsRemaining);

                if(nearingDistance ||  nearingDuration){
                     expiringParts.add(summary.build());
                }else{
                    goodParts.add(summary.build());
                }
            }
        }
        return PartsStatus.builder().expiringParts(expiringParts).expiredParts(expiredParts).goodParts(goodParts).build();
    }
}
