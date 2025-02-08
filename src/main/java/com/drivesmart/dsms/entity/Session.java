package com.drivesmart.dsms.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Session {
    private String sessionDate;
    private int vehicleId;
    private int employeeId;
    private int studentId;
    private String sessionTime;
}