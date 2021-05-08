package com.strawberrye.se.backend.reportsavelambda.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataGroup {

    private Instant startTime;
    private Instant endTime;
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
