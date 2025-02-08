package com.drivesmart.dsms.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Vehicle {
    private String vehicleType;
    private String make;
    private String model;
    private String transmission;
    private String licensePlate;
}
