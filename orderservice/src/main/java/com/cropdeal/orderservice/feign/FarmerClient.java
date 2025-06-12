package com.cropdeal.orderservice.feign;

import com.cropdeal.orderservice.dto.FarmerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "farmer-service", url = "http://localhost:8080")
public interface FarmerClient {
    @GetMapping("/api/farmers/{id}")
    FarmerDTO getFarmerById(@PathVariable("id") String id);
}
