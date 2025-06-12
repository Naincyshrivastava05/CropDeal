package com.cropdeal.payment_service.service;

import com.cropdeal.payment_service.dto.PaymentRequest;
import com.cropdeal.payment_service.dto.PaymentResponse;

public interface PaymentService {
    PaymentResponse processPayment(PaymentRequest request);
}
