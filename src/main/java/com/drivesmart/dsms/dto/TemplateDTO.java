package com.drivesmart.dsms.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TemplateDTO {
    private int templateId;
    private String name;
    private String description;
}
