package com.cropdeal.orderservice.message;

import com.cropdeal.orderservice.service.OrderService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PaymentMessageListener {

    private final OrderService orderService;

    public PaymentMessageListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void handlePaymentMessage(Map<String, String> message) {
        System.out.println("Received message: " + message);
        String orderId = message.get("orderId");
        String status = message.get("status");


        orderService.updateStatus(Long.parseLong(orderId), status);


 if ("SUCCESS".equalsIgnoreCase(status)) {
 orderService.updatePaymentStatus(Long.parseLong(orderId), "Paid");
 }

     System.out.println("Order " + orderId + " updated payment status to Paid and Status to " + status);
        System.out.println("Received message: " + message);

    }
}
