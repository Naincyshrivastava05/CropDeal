package com.cropdeal.notification_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CropPublishedEvent {
    private String cropId;
    private String cropType;
    private String farmerId;
    private String location;
    private int quantity;

    // Getters and setters
}

