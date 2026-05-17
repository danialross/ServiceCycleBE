package com.danialross.ServiceCycle.health;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HealthController {

    @GetMapping("/health-check")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The server is running"),
            @ApiResponse(responseCode = "400", description = "The server is dead"),
    })
    public String healthCheck(){
        return "Server is up and running!!!";
    }
}

