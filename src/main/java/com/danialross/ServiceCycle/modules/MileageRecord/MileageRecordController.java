package com.danialross.ServiceCycle.modules.MileageRecord;

import com.danialross.ServiceCycle.modules.MileageRecord.dto.CreateMileageRecordDTO;
import com.danialross.ServiceCycle.modules.MileageRecord.dto.MileageResponse;
import com.danialross.ServiceCycle.modules.MileageRecord.dto.UpdateMileageRecordDTO;
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
public class MileageRecordController {
    private final MileageRecordService mileageRecordService;

    @Operation(summary = "Add a mileage record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Record retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),

    })
    @PostMapping("/mileage")
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
            @ApiResponse(responseCode = "404", description = "Record Not Found"),

    })
    @PostMapping("/mileage/{mileageRecordId}")
    public ResponseEntity<MileageResponse> update(@AuthenticationPrincipal Jwt payload,@PathVariable UUID mileageRecordId,@Valid @RequestBody UpdateMileageRecordDTO mileageRecordDTO){
        UUID userId = UUID.fromString(payload.getSubject());MileageRecord response = mileageRecordService.update(userId,mileageRecordId,mileageRecordDTO);
        return ResponseEntity.ok(MileageResponse.fromRecord(response));
    }

    @Operation(summary = "Delete a mileage record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Record deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Record Not Found"),

    })
    @DeleteMapping("/mileage/{mileageRecordId}")
    public ResponseEntity<UUID> delete(@AuthenticationPrincipal Jwt payload,@PathVariable UUID mileageRecordId){
        UUID userId = UUID.fromString(payload.getSubject());
        UUID deletedRecord = mileageRecordService.delete(userId,mileageRecordId);
        return ResponseEntity.ok(deletedRecord);
    }

    @Operation(summary = "Get current vehicle mileage record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get mileage record successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Record Not Found"),

    })
    @GetMapping("/mileage/vehicle/{vehicleId}")
    public ResponseEntity<MileageResponse> get(UUID vehicleId){
        MileageRecord record = mileageRecordService.get(vehicleId);
        return ResponseEntity.ok(MileageResponse.fromRecord(record));
    }
}
