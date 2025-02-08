package com.drivesmart.dsms.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTO {
    private String username;
    private String password;
    private String role;
    private String status;
}
