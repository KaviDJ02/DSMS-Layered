package com.drivesmart.dsms.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Package {
    private int packageId;
    private String packageName;
    private String description;
    private int duration;
    private double price;
    private int employeeId;

    public Package(String packageName, String description, int duration, double price, int employeeId) {
        this.packageName = packageName;
        this.description = description;
        this.duration = duration;
        this.price = price;
        this.employeeId = employeeId;
    }
}
