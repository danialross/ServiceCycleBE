package com.danialross.ServiceCycle.modules.parts;

import com.danialross.ServiceCycle.modules.parts.dto.CreatePartDTO;
import com.danialross.ServiceCycle.modules.parts.dto.UpdatePartDTO;
import com.danialross.ServiceCycle.modules.parts.enums.PartType;
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
        boolean hasPositionOrIndex = dto.getIndex() != null || dto.getPosition() != null;


        if (hasPositionedParts && !hasPosition)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    PartType.positionedParts + " must have positions");

        if (hasIndexedParts && !hasIndexes)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    PartType.indexedParts + " must have indexes");

        if((!hasPositionedParts || !hasIndexedParts) && hasPositionOrIndex)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    dto.getType() + " cannot have position or indexes");
    }
}
