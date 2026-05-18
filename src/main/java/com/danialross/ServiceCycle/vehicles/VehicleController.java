package com.danialross.ServiceCycle.vehicles;

import com.danialross.ServiceCycle.vehicles.dto.CreateVehicleDTO;
import com.danialross.ServiceCycle.vehicles.dto.UpdateVehicleDTO;
import com.danialross.ServiceCycle.vehicles.dto.VehicleQueryDTO;
import com.danialross.ServiceCycle.vehicles.dto.VehicleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vehicle")
public class VehicleController {
    private final VehicleService vehicleService;

    @Operation(summary = "Register a vehicle to owner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle created successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "409", description = "Vehicle with license plate already exist")
    })
    @PostMapping()
    public ResponseEntity<VehicleResponse> register(@AuthenticationPrincipal Jwt payload,@Valid @RequestBody CreateVehicleDTO vehicle){
        UUID ownerId = UUID.fromString(payload.getSubject());
        VehicleResponse response = vehicleService.register(ownerId, vehicle);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Update details for owner's existing vehicle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle updated"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Vehicle with id does not exist")
    })
    @PostMapping("/update")
    public ResponseEntity<VehicleResponse> update(@AuthenticationPrincipal Jwt payload,@Valid @RequestBody UpdateVehicleDTO vehicle){
        UUID ownerId = UUID.fromString(payload.getSubject());
        VehicleResponse response = vehicleService.update(ownerId, vehicle);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Delete owner's existing vehicle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle deleted"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Vehicle with id does not exist")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<VehicleResponse> update(@AuthenticationPrincipal Jwt payload,@PathVariable UUID id){
        UUID ownerId = UUID.fromString(payload.getSubject());
        VehicleResponse response = vehicleService.delete(ownerId, id);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Find all existing vehicles with query parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle retrieved"),
            @ApiResponse(responseCode = "400", description = "Bad query parameter"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })
    @Validated
    @GetMapping()
    public ResponseEntity<List<VehicleResponse>> findAll(@AuthenticationPrincipal Jwt payload,@Valid VehicleQueryDTO queries){
        UUID ownerId = UUID.fromString(payload.getSubject());

        List<VehicleResponse> response = vehicleService.findAll(ownerId, queries);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Find an existing vehicle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle retrieved"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Vehicle does not exist"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponse> find(@AuthenticationPrincipal Jwt payload,@PathVariable UUID id){
        UUID ownerId = UUID.fromString(payload.getSubject());
        VehicleResponse response = vehicleService.findByIdAndOwnerId(ownerId,id);
        return ResponseEntity.ok().body(response);
    }
}
