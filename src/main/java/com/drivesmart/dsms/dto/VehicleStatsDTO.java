package com.drivesmart.dsms.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VehicleStatsDTO {
    private int vehicleId;
    private String status;
    private String timestamp;
}
