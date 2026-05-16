package com.danialross.ServiceCycle.health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HealthController {
    @GetMapping("/health-check")
    public String healthCheck(){
        return "Server is up and running!!!";
    }
}

