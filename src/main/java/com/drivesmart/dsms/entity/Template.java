package com.drivesmart.dsms.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Template {
    private int templateId;
    private String name;
    private String description;
}
