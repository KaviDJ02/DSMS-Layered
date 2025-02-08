package com.drivesmart.dsms.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PackageDTO {
    private int packageId;
    private String packageName;
    private String description;
    private int duration;
    private double price;
    private int employeeId;

    public PackageDTO(String packageName, String description, int duration, double price, int employeeId) {
        this.packageName = packageName;
        this.description = description;
        this.duration = duration;
        this.price = price;
        this.employeeId = employeeId;
    }
}
