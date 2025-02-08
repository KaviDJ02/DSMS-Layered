package com.drivesmart.dsms.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PackageEnrollment {
    private int enrollmentId;
    private int studentId;
    private int packageId;
    private String enrollmentDate;
    private String status;
}
