package com.drivesmart.dsms.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SessionAttendanceDTO {
    private int attendanceId;
    private int sessionId;
    private int studentId;
    private String attendanceStatus;
}
