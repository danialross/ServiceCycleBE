package com.danialross.ServiceCycle.modules.mileageRecord;

import com.danialross.ServiceCycle.modules.mileageRecord.dto.CreateMileageRecordDTO;
import com.danialross.ServiceCycle.modules.mileageRecord.dto.MileageResponse;
import com.danialross.ServiceCycle.modules.mileageRecord.dto.UpdateMileageRecordDTO;
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
@RequestMapping("/api/mileage")
public class MileageRecordController {
    private final MileageRecordService mileageRecordService;

    @Operation(summary = "Add a mileage record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Record retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),

    })
    @PostMapping()
    public ResponseEntity<MileageResponse> add(@AuthenticationPrincipal Jwt payload,@Valid @RequestBody CreateMileageRecordDTO mileageRecordDTO){
        UUID userId = UUID.fromString(payload.getSubject());
        MileageRecord response = mileageRecordService.add(userId,mileageRecordDTO);
        return ResponseEntity.ok(MileageResponse.fromRecord(response));
    }

    @Operation(summary = "Update a mileage record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Record retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Mileage smaller than entry before"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "User does not have permission to access"),
            @ApiResponse(responseCode = "404", description = "Record Not Found"),

    })
    @PostMapping("/{mileageRecordId}")
    public ResponseEntity<MileageResponse> update(@AuthenticationPrincipal Jwt payload,@PathVariable UUID mileageRecordId,@Valid @RequestBody UpdateMileageRecordDTO mileageRecordDTO){
        UUID userId = UUID.fromString(payload.getSubject());
        MileageRecord response = mileageRecordService.update(userId,mileageRecordId,mileageRecordDTO);
        return ResponseEntity.ok(MileageResponse.fromRecord(response));
    }

    @Operation(summary = "Delete a mileage record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Record deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "User does not have permission to access"),
            @ApiResponse(responseCode = "404", description = "Record Not Found"),

    })
    @DeleteMapping("/{mileageRecordId}")
    public ResponseEntity<UUID> delete(@AuthenticationPrincipal Jwt payload,@PathVariable UUID mileageRecordId){
        UUID userId = UUID.fromString(payload.getSubject());
        UUID deletedRecord = mileageRecordService.delete(userId,mileageRecordId);
        return ResponseEntity.ok(deletedRecord);
    }

    @Operation(summary = "Get mileage record using id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get mileage record successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "User does not have permission to access"),
            @ApiResponse(responseCode = "404", description = "Record Not Found"),

    })
    @GetMapping("/{mileageRecordId}")
    public ResponseEntity<MileageResponse> getMileageRecord(@AuthenticationPrincipal Jwt payload, UUID mileageRecordId){
        UUID userId = UUID.fromString(payload.getSubject());
        MileageRecord record = mileageRecordService.findOneWithAuthCheck(userId,mileageRecordId);
        return ResponseEntity.ok(MileageResponse.fromRecord(record));
    }

    @Operation(summary = "Get latest vehicle mileage record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get mileage record successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "User does not have permission to access"),
            @ApiResponse(responseCode = "404", description = "Record Not Found"),

    })
    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<MileageResponse> getLatestMileageRecord(@AuthenticationPrincipal Jwt payload, UUID vehicleId){
        UUID userId = UUID.fromString(payload.getSubject());
        MileageRecord record = mileageRecordService.getLatestMileageRecord(userId,vehicleId);
        return ResponseEntity.ok(MileageResponse.fromRecord(record));
    }


    @Operation(summary = "Get all mileage records for vehicle with id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get mileage records successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "User does not have permission to access"),
            @ApiResponse(responseCode = "404", description = "Record Not Found"),

    })
    @GetMapping("/vehicle/{vehicleId}/all")
    public ResponseEntity<List<MileageResponse>> getAllMileageRecord(@AuthenticationPrincipal Jwt payload, UUID vehicleId){
        UUID userId = UUID.fromString(payload.getSubject());
        List<MileageRecord> records = mileageRecordService.findAllWithAuthCheck(userId,vehicleId);
        List<MileageResponse> response = new ArrayList<>();

        for(MileageRecord record : records){
            response.add(MileageResponse.fromRecord(record));
        }

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get monthly average mileage for vehicle with id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get mileage records successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "User does not have permission to access"),
            @ApiResponse(responseCode = "404", description = "Record Not Found"),

    })
    @GetMapping("/vehicle/{vehicleId}/monthly-usage")
    public ResponseEntity<Float> getMonthlyUsage(@AuthenticationPrincipal Jwt payload, UUID vehicleId){
        UUID userId = UUID.fromString(payload.getSubject());
        Float averageMileagePerMonth = mileageRecordService.getMonthlyUsage(userId,vehicleId);
        return ResponseEntity.ok(averageMileagePerMonth);
    }
}
