package com.strawberrye.se.backend.reportsavelambda.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoreSystemReport {

    private int deviceId;
    private Instant sendingTime;

    private String softwareVersion;
    private int systemMode;

//    private List<DataGroup> dataGroups;

}

