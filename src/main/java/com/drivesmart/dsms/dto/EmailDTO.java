package com.drivesmart.dsms.dto;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmailDTO {
    private String subject;
    private String title;
    private String sentDate;
    private int studentId;
    private int templateId;
}
