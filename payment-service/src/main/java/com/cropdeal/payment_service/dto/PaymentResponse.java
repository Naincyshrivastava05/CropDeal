package com.cropdeal.payment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {
    private String message;
    private String transactionId;
    private String status;
    private String sessionUrl;

    public PaymentResponse(String paymentSuccessful, String s) {
        this.message = paymentSuccessful;
        this.transactionId = s;
    }
}
