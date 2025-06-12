package com.cropdeal.cropservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FarmerDTO {
    private Long id;
    private String name;
    private String email;
}
