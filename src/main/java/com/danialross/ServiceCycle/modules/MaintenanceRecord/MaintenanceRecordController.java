package com.danialross.ServiceCycle.modules.MaintenanceRecord;

import com.danialross.ServiceCycle.modules.MaintenanceRecord.dto.CreateMaintenanceDTO;
import com.danialross.ServiceCycle.modules.MaintenanceRecord.dto.MaintenanceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("maintenance")
public class MaintenanceRecordController {
    private final MaintenanceRecordService maintenanceRecordService;

    @Operation(summary = "Add a new maintenance record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Record created successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
     })
    @PostMapping("/add")
    private ResponseEntity<MaintenanceResponse> add(@AuthenticationPrincipal Jwt payload,@Valid @RequestBody CreateMaintenanceDTO dto){
        UUID ownerId = UUID.fromString(payload.getSubject());
        MaintenanceRecord maintenance = maintenanceRecordService.add(ownerId,dto);
        MaintenanceResponse response = MaintenanceResponse.fromMaintenance(maintenance);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Retrieve a maintenance record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Record retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })
    @GetMapping("/{maintenanceId}")
    private ResponseEntity<MaintenanceResponse> get(@AuthenticationPrincipal Jwt payload,@PathVariable UUID maintenanceId){
        UUID ownerId = UUID.fromString(payload.getSubject());
        MaintenanceRecord maintenance = maintenanceRecordService.getByOwner(ownerId,maintenanceId);
        MaintenanceResponse response = MaintenanceResponse.fromMaintenance(maintenance);
        return ResponseEntity.ok().body(response);
    }
}
