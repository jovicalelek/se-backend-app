package com.strawberrye.se.backend.reportservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoreSystemReport {

    private int softwareVersion;
    private int deviceId;
    private Timestamp sendingTime;
    private int batteryMode;
    private int systemMode;

    private List<DataGroup> dataGroups;

}
