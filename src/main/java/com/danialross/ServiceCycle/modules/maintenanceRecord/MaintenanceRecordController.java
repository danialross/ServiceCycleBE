package com.danialross.ServiceCycle.modules.maintenanceRecord;

import com.danialross.ServiceCycle.modules.maintenanceRecord.dto.CreateMaintenanceDTO;
import com.danialross.ServiceCycle.modules.maintenanceRecord.dto.MaintenanceResponse;
import com.danialross.ServiceCycle.modules.maintenanceRecord.dto.UpdateMaintenanceDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/maintenance")
public class MaintenanceRecordController {
    private final MaintenanceRecordService maintenanceRecordService;

    @Operation(summary = "Add a new maintenance record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Record created successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
    })
    @PostMapping("/")
    public ResponseEntity<MaintenanceResponse> add(@AuthenticationPrincipal Jwt payload,@Valid @RequestBody CreateMaintenanceDTO dto){
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
    @GetMapping("/{maintenanceRecordId}")
    public ResponseEntity<MaintenanceResponse> get(@AuthenticationPrincipal Jwt payload,@PathVariable UUID maintenanceRecordId){
        UUID ownerId = UUID.fromString(payload.getSubject());
        MaintenanceRecord maintenance = maintenanceRecordService.findOneWithAccessCheck(ownerId,maintenanceRecordId);
        MaintenanceResponse response = MaintenanceResponse.fromMaintenance(maintenance);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Retrieve all maintenance records")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Record retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "User doesn't have access"),
    })
    @GetMapping("/{maintenanceRecordId}/all")
    public ResponseEntity<List<MaintenanceResponse>> getAll(@AuthenticationPrincipal Jwt payload, @PathVariable UUID maintenanceRecordId){
        UUID ownerId = UUID.fromString(payload.getSubject());
        List<MaintenanceRecord> records = maintenanceRecordService.findAllWithAccessCheck(ownerId,maintenanceRecordId);
        List<MaintenanceResponse> response = new ArrayList<>();
        for(MaintenanceRecord record : records ){
            response.add(MaintenanceResponse.fromMaintenance(record));
        }
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Update a maintenance record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Record updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
    })
    @PostMapping("/{maintenanceRecordId}")
    public ResponseEntity<MaintenanceResponse> update(@AuthenticationPrincipal Jwt payload,@PathVariable UUID maintenanceRecordId,@Valid @RequestBody UpdateMaintenanceDTO dto){
        UUID userId = UUID.fromString(payload.getSubject());
        MaintenanceRecord maintenance = maintenanceRecordService.update(userId,maintenanceRecordId,dto);
        MaintenanceResponse response = MaintenanceResponse.fromMaintenance(maintenance);
        return ResponseEntity.ok().body(response);
    }
}
