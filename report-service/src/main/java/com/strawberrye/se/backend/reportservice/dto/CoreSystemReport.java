package com.strawberrye.se.backend.reportservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoreSystemReport {

    private int softwareVersion;
    private int deviceId;
    private Instant sendingTime;
    private int batteryMode;
    private int systemMode;

    private List<DataGroup> dataGroups;

}
