package com.danialross.ServiceCycle.modules.MileageRecord;

import com.danialross.ServiceCycle.modules.MileageRecord.dto.CreateMileageRecordDTO;
import com.danialross.ServiceCycle.modules.MileageRecord.dto.MileageResponse;
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
}
