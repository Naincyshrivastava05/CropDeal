package com.cropdeal.payment_service.message;

import com.cropdeal.payment_service.dto.PaymentMessage;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

//@Component
//public class PaymentPublisher {
//
//    private final AmqpTemplate rabbitTemplate;
//
//    @Value("${rabbitmq.exchange}")
//    private String exchange;
//
//    @Value("${rabbitmq.routingkey}")
//    private String routingKey;
//
//    public PaymentPublisher(AmqpTemplate rabbitTemplate) {
//        this.rabbitTemplate = rabbitTemplate;
//    }
//
//    public void publishPaymentSuccess(String orderId) {
//        Map<String, String> message = new HashMap<>();
//        message.put("orderId", orderId);
//        message.put("status", "SUCCESS");
//
//        rabbitTemplate.convertAndSend(exchange, routingKey, message);
//        System.out.println("Published payment success for order " + orderId);
//    }
//}
@Component
public class PaymentPublisher {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routingkey}")
    private String routingKey;

    public void publishPaymentSuccess(PaymentMessage message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
        System.out.println("Published payment success for order " + message.getOrderId());
    }
}
