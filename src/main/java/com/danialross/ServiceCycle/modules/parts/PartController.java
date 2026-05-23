package com.danialross.ServiceCycle.modules.parts;

import com.danialross.ServiceCycle.modules.parts.dto.CreatePartDTO;
import com.danialross.ServiceCycle.modules.parts.dto.PartResponse;
import com.danialross.ServiceCycle.modules.vehicles.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/part")
@RequiredArgsConstructor
public class PartController {
    private final PartService partService;
    private final VehicleService vehicleService;

    @Operation(summary = "Create a new part for existing vehicle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Part is created"),
            @ApiResponse(responseCode = "400", description = "Part validity not valid, must have time or distance"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Vehicle does not exist"),
    })
    @PostMapping()
    public ResponseEntity<PartResponse> create(@AuthenticationPrincipal Jwt payload,@Valid CreatePartDTO createPartDTO){
        UUID ownerId = UUID.fromString(payload.getSubject());

        vehicleService.checkVehicleWithOwnerExist(createPartDTO.getVehicleId(),ownerId);

        if(createPartDTO.getValidityTime() == null && createPartDTO.getValidityDistance() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Either a time or distance constraint for the part must be given");
        }

        Part newPart = partService.register(ownerId,createPartDTO);
        PartResponse response = PartResponse.fromPart(newPart);

        return ResponseEntity.ok().body(response);
    }
}
