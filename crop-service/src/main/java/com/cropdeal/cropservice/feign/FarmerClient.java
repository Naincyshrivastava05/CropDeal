package com.cropdeal.cropservice.feign;


import com.cropdeal.cropservice.dto.FarmerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "farmer-service")
public interface FarmerClient {

 @GetMapping("/api/farmers/{id}")
 ResponseEntity<FarmerDTO> getFarmerById(@PathVariable("id") Long id);

}
