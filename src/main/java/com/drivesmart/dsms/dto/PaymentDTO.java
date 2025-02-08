package com.drivesmart.dsms.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentDTO {
    private int studentId;
    private double amount;
    private double amountDue;
    private String receivedDate;
    private String status;
}
