package com.cropdeal.payment_service.dto;


import lombok.Data;
import java.io.Serializable;
@Data
public class PaymentMessage implements Serializable {
    private String orderId;
    private String status;
}
