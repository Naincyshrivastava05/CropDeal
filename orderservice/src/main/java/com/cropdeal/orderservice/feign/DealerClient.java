package com.cropdeal.orderservice.feign;

import com.cropdeal.orderservice.dto.DealerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "dealer-service", url = "http://localhost:8082")
public interface DealerClient {
    @GetMapping("/api/dealers/{id}")
    DealerDTO getDealerById(@PathVariable("id") String id);
}
