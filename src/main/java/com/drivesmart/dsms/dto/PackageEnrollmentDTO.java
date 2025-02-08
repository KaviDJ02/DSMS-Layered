package com.drivesmart.dsms.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PackageEnrollmentDTO {
    private int enrollmentId;
    private int studentId;
    private int packageId;
    private String enrollmentDate;
    private String status;
}
