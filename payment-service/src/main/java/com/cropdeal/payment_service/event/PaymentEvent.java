package com.cropdeal.payment_service.event;

import java.io.Serializable;

public class PaymentEvent implements Serializable {
    private static final long serialVersionUID = 1L;  // Recommended for Serializable classes

    private Long paymentId;
    private String eventType;

    public PaymentEvent(Long paymentId, String eventType) {
        this.paymentId = paymentId;
        this.eventType = eventType;
    }

    // Getters and setters
    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
