package com.cropdeal.payment_service.event;

import com.cropdeal.payment_service.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EventPublisher {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${payment.queue.name}")
    private String queueName;

    public void publish(PaymentEvent event){
        rabbitTemplate.convertAndSend(queueName,event);
    }
}
