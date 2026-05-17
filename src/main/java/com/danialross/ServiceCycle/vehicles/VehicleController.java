package com.danialross.ServiceCycle.vehicles;

import com.danialross.ServiceCycle.vehicles.dto.CreateVehicleDTO;
import com.danialross.ServiceCycle.vehicles.dto.VehicleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vehicle")
public class VehicleController {
    private final VehicleService vehicleService;

    @Operation(summary = "Register a vehicle")
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

}
