package com.danialross.ServiceCycle.modules.parts;

import com.danialross.ServiceCycle.modules.mileageRecord.MileageRecord;
import com.danialross.ServiceCycle.modules.mileageRecord.MileageRecordService;
import com.danialross.ServiceCycle.modules.parts.dto.*;
import com.danialross.ServiceCycle.modules.parts.enums.PartPosition;
import com.danialross.ServiceCycle.modules.parts.enums.PartType;
import com.danialross.ServiceCycle.modules.parts.interfaces.PartWithInstalledMileage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;


@Service
@RequiredArgsConstructor
public class PartService {
    private final PartRepository partRepository;
    private final MileageRecordService mileageRecordService;

    //Creation, Update and Delete happens by changing the maintenance record itself
    public void validatePart(CreatePartDTO dto) {

        //parts with multiple must have position and index else position and index must be null
        boolean hasPositionedParts = PartType.positionedParts.contains(PartType.valueOf(dto.getType()));
        boolean hasIndexedParts = PartType.indexedParts.contains(PartType.valueOf(dto.getType()));

        boolean hasPosition = dto.getPosition() != null;
        boolean hasIndexes = dto.getIndex() != null;
        StringBuilder error = new StringBuilder();

        if (hasPositionedParts) {
            if (!hasPosition) error.append(PartType.positionedParts).append(" must have positions\n");
            if (hasIndexes) error.append(PartType.positionedParts).append(" cant have index\n");
        }

        if (hasIndexedParts) {
            if (!hasIndexes) error.append(PartType.indexedParts).append(" must have indexes\n");
            if (hasPosition) error.append(PartType.indexedParts).append(" cant have positions\n");
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

    public void deactivateLatest(UUID vehicleId, PartType type,PartPosition position,Integer index){
        partRepository.findLatestActive(vehicleId,type, position,index)
                .ifPresent(part -> {
                    part.setIsActive(false);
                    partRepository.save(part);
                });
    }

    public void activatePart(UUID vehicleId,PartType type, PartPosition position, Integer index){
        partRepository.findPreviousActivePart(vehicleId,type,position,index)
                .ifPresent(part -> {
                    part.setIsActive(true);
                    partRepository.save(part);
                });
    }

    public Part getPart(UUID partId){
        return partRepository.findById(partId).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Part with Id: " + partId + " not found"));
    }

    public void checkDuplicateParts(List<CreatePartDTO> dto){
        Set<String> alreadySeenParts = new HashSet<>();
        for( CreatePartDTO part : dto){

            StringBuilder builder = new StringBuilder();
            builder.append(part.getType());

            if(part.getPosition() != null) builder.append("_").append(part.getPosition());
            if(part.getIndex() != null) builder.append("_").append(part.getIndex());

            String partName = builder.toString();

            if(alreadySeenParts.contains(partName)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Request contain multiples of the same parts");
            alreadySeenParts.add(partName);
        }
    }
}
