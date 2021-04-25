package com.strawberrye.se.backend.reportservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataGroup {

    private Timestamp startTime;
    private Timestamp endTime;
    private int batteryMode;
    private int systemMode;

    // In mV
    private int batteryVoltageMean;
    private int batteryVoltageMin;
    private int batteryVoltageMax;

    // m°C
    private int temperatureMean;
    private int temperatureMin;
    private int temperatureMax;

    private List<ChargerReport> chargerReports;
    private List<FuseReport> fuseReports;

}
