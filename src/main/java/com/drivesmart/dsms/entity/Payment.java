package com.drivesmart.dsms.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Payment {
    private int studentId;
    private double amount;
    private double amountDue;
    private String receivedDate;
    private String status;
}
