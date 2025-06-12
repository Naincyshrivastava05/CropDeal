package com.cropdeal.payment_service.dto;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    public Long amount;
    private  Long quantity;
    private  String name;
    private String currency;
    private  Long farmerId;
    private Long dealerId;
    private String orderId;
}
