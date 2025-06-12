package com.cropdeal.notification_service.controller;

import com.cropdeal.notification_service.model.CropPublishedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// controller/TestPublisherController.java
@RestController
@RequestMapping("/test")
public class TestPublisherController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routingkey}")
    private String routingKey;

    @PostMapping("/publish")
    public String publishEvent(@RequestBody CropPublishedEvent event) {
        rabbitTemplate.convertAndSend(exchange, routingKey, event);
        return "Event published!";
    }
}

