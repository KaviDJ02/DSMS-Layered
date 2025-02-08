package com.drivesmart.dsms.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeDTO{
    private int userId;
    private String name;
    private String email;
    private String phone;
    private String nic;
    private String position;
    private String salary;


}

