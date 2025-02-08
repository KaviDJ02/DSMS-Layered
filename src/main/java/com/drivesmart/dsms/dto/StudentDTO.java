package com.drivesmart.dsms.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentDTO {
    private String name;
    private String email;
    private String phone;
    private String address;
    private String birthday;
    private String nic;
}
