package com.cropdeal.payment_service.controller;

import com.cropdeal.payment_service.dto.PaymentMessage;
import com.cropdeal.payment_service.dto.PaymentRequest;
import com.cropdeal.payment_service.dto.PaymentResponse;
import com.cropdeal.payment_service.message.PaymentPublisher;
import com.cropdeal.payment_service.service.PaymentService;
import com.cropdeal.payment_service.service.impl.PaymentServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentServiceImpl stripeService;

    @Autowired
    private PaymentService paymentService;

    public PaymentController(PaymentServiceImpl service) {
        this.stripeService = service;
    }

    @PostMapping("/checkout")
    public ResponseEntity<PaymentResponse> makePayment(@RequestBody PaymentRequest request) {
        log.info("Received payment request: {}", request);
        PaymentResponse response = paymentService.processPayment(request);
        log.info("Payment processed with transaction ID: {}", response.getTransactionId());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<String> getPayments() {
        log.info("Health check endpoint hit");
        return ResponseEntity.ok("Payments service is running!");
    }


        @Autowired
        private PaymentPublisher paymentPublisher;


    @PostMapping("/success")
    public ResponseEntity<String> markPaymentSuccess(@RequestBody PaymentMessage paymentMessage) {
 paymentPublisher.publishPaymentSuccess(paymentMessage);
 return ResponseEntity.ok("Payment marked as successful for order " + paymentMessage.getOrderId());
    }

}


