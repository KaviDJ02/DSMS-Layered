package com.drivesmart.dsms.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SessionDTO {
    private String sessionDate;
    private int vehicleId;
    private int employeeId;
    private int studentId;
    private String sessionTime;
}