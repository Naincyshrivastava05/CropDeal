package com.cropdeal.payment_service.service.impl;

import com.cropdeal.payment_service.dto.PaymentMessage;
import com.cropdeal.payment_service.dto.PaymentRequest;
import com.cropdeal.payment_service.dto.PaymentResponse;
import com.cropdeal.payment_service.entity.Payment;
import com.cropdeal.payment_service.event.EventPublisher;
import com.cropdeal.payment_service.event.PaymentEvent;
import com.cropdeal.payment_service.feign.OrderClient;
import com.cropdeal.payment_service.message.PaymentPublisher;
//import com.cropdeal.payment_service.message.PaymentMessage;
import com.cropdeal.payment_service.repository.PaymentRepository;
import com.cropdeal.payment_service.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${stripe.secretKey}")
    private String secretKey;

    @Autowired
    private PaymentRepository paymentRepo;

    @Autowired
    private EventPublisher eventPublisher;


    @Autowired
    private OrderClient orderClient;

    @Autowired
    private PaymentPublisher paymentPublisher;


    public void handleStripeSuccess(String orderId) {
        log.info("Handling Stripe success for order ID: {}", orderId);

        PaymentMessage message = new PaymentMessage();
        message.setOrderId(orderId);
        message.setStatus("SUCCESS");

        paymentPublisher.publishPaymentSuccess(message);
    }

    @Override
    public PaymentResponse processPayment(PaymentRequest request) {
        log.info("Starting payment processing for: {}", request.getName());

        // Step 1: Check if order exists via Feign Client
        try {
            ResponseEntity<String> response = orderClient.checkOrderExists(request.getOrderId());

            if (!response.getStatusCode().is2xxSuccessful()) {
                return PaymentResponse.builder()
                        .status("FAILED")
                        .message("Order not found with ID: " + request.getOrderId())
                        .transactionId("N/A")
                        .sessionUrl("N/A")
                        .build();
            }
        } catch (Exception ex) {
            log.error("Order check failed: {}", ex.getMessage());
            return PaymentResponse.builder()
                    .status("FAILED")
                    .message("Order not found or Order Service unavailable for ID: " + request.getOrderId())
                    .transactionId("N/A")
                    .sessionUrl("N/A")
                    .build();
        }

        // Step 2: Proceed with Stripe payment
        Stripe.apiKey = secretKey;

        SessionCreateParams.LineItem.PriceData.ProductData productData =
                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName(request.getName())
                        .build();

        SessionCreateParams.LineItem.PriceData priceData =
                SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency(request.getCurrency() == null ? "USD" : request.getCurrency())
                        .setUnitAmount(request.getAmount())
                        .setProductData(productData)
                        .build();

        SessionCreateParams.LineItem lineItem =
                SessionCreateParams.LineItem.builder()
                        .setQuantity(request.getQuantity())
                        .setPriceData(priceData)
                        .build();

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8081/success")
                .setCancelUrl("http://localhost:8081/cancel")
                .addLineItem(lineItem)
                .build();

        try {
            Session session = Session.create(params);
            log.info("Stripe session created successfully: {}", session.getId());

            return PaymentResponse.builder()
                    .status("SUCCESS")
                    .message("Payment session created")
                    .transactionId(session.getId())
                    .sessionUrl(session.getUrl())
                    .build();

        } catch (StripeException ex) {
            log.error("StripeException occurred while creating session: {}", ex.getMessage(), ex);
            return PaymentResponse.builder()
                    .status("FAILED")
                    .message("Payment session creation failed")
                    .transactionId("N/A")
                    .sessionUrl("N/A")
                    .build();
        }
    }

}
