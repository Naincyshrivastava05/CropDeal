package com.cropdeal.notification_service.listener;

import com.cropdeal.notification_service.model.CropPublishedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

// listener/NotificationListener.java
@Service
public class NotificationListener {

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void handleCropPublished(CropPublishedEvent event) {
        System.out.println("📢 New crop published: " + event.getCropType() +
                " at " + event.getLocation() + " by Farmer " + event.getFarmerId());

        // TODO: Notify dealers (email, SMS, push, etc.)
    }
}
