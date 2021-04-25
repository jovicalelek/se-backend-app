package com.strawberrye.se.backend.reportservice.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
@Slf4j
public class HealthCheckController {

    @GetMapping
    public String check() {
        log.info("Health check passed. Status: HEALTHY.");
        return "HEALTHY";
    }

}
