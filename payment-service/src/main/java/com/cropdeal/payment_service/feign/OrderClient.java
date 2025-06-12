package com.cropdeal.payment_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "orderservice", url = "http://localhost:8087")
public interface OrderClient {

    @GetMapping("/orders/{orderId}")
    ResponseEntity<String> checkOrderExists(@PathVariable("orderId") String orderId);
}

