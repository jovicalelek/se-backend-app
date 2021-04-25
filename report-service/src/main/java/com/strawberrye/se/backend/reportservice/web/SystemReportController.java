package com.strawberrye.se.backend.reportservice.web;

import com.strawberrye.se.backend.reportservice.dto.CoreSystemReport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports/core/system/v1")
@Slf4j
public class SystemReportController {

    @PostMapping("/{deviceId}")
    public CoreSystemReport postReport(@PathVariable int deviceId, @RequestBody CoreSystemReport report) {
        log.info("Device ID: {}", deviceId);
        log.info("Core System Report:\n\n{}", report);
        log.info("\n");
        return report;
    }

}
