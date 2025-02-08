package com.drivesmart.dsms.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VehicleStats {
    private int vehicleId;
    private String status;
    private String timestamp;
}
