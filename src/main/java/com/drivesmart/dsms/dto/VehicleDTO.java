package com.drivesmart.dsms.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VehicleDTO {
    private String vehicleType;
    private String make;
    private String model;
    private String transmission;
    private String licensePlate;
}
