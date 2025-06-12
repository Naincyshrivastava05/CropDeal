package com.cropdeal.orderservice.feign;

import com.cropdeal.orderservice.dto.CropDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "crop-service", url = "http://localhost:8085")
public interface CropClient {
    @GetMapping("/crops/{id}")
    CropDTO getCropById(@PathVariable("id") String id);
}

