package com.drivesmart.dsms.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Student {
    private String name;
    private String email;
    private String phone;
    private String address;
    private String birthday;
    private String nic;
}
