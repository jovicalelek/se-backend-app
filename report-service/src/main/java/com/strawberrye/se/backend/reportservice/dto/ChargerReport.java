package com.strawberrye.se.backend.reportservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChargerReport {

    private int chargerId;
    private int numberOfUsers;

}
