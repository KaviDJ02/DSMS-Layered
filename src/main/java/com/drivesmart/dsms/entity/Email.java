package com.drivesmart.dsms.entity;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Email {
    private String subject;
    private String title;
    private String sentDate;
    private int studentId;
    private int templateId;
}
