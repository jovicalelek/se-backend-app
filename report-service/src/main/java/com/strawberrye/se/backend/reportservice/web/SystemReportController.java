package com.strawberrye.se.backend.reportservice.web;

import com.strawberrye.se.backend.reportservice.dto.CoreSystemReport;
import com.strawberrye.se.backend.reportservice.sns.CoreSystemReportSnsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports/core/system/v1")
@Slf4j
public class SystemReportController {

    private final CoreSystemReportSnsService reportSnsService;

    public SystemReportController(CoreSystemReportSnsService reportSnsService) {
        this.reportSnsService = reportSnsService;
    }

    @PostMapping("/{deviceId}")
    public CoreSystemReport postReport(@PathVariable int deviceId, @RequestBody CoreSystemReport report) {
        log.info("Device ID: {}", deviceId);
        log.info("Core system report:");
        log.info("{}", report);

        if (reportSnsService.publishCoreSystemReport(report)) {
            log.info("Core system report published successfully");
        } else {
            log.info("Core system report publishing failed");
        }

        return report;
    }

}
