package com.drivesmart.dsms.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SessionAttendance {
    private int attendanceId;
    private int sessionId;
    private int studentId;
    private String attendanceStatus;
}
