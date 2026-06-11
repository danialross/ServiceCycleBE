package com.danialross.ServiceCycle.modules.parts;

import com.danialross.ServiceCycle.modules.maintenanceRecord.MaintenanceRecord;
import com.danialross.ServiceCycle.modules.maintenanceRecord.dto.MaintenanceResponse;
import com.danialross.ServiceCycle.modules.parts.dto.PartResponse;
import com.danialross.ServiceCycle.modules.parts.dto.PartsStatus;
import com.danialross.ServiceCycle.modules.vehicles.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/part")
public class PartController {
    private final PartService partService;
    private final VehicleService vehicleService;

    @Operation(summary = "Get status of all parts attached to vehicel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Parts retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "User doesn't have access"),
    })
    @GetMapping("/status/{vehicleId}")
    public ResponseEntity<PartsStatus> getVehiclePartsStatus(@AuthenticationPrincipal Jwt payload, @PathVariable UUID vehicleId){
        UUID userId = UUID.fromString(payload.getSubject());
        vehicleService.checkAccess(vehicleId,userId);
        PartsStatus partsStatus = partService.getPartsStatus(userId,vehicleId);
        return ResponseEntity.ok(partsStatus);

    }
}
