package com.cropdeal.orderservice.controller;


import com.cropdeal.orderservice.entity.Order;
import com.cropdeal.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/placeOrder")
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @GetMapping("/")
    public String getMessage() {
        return "Yes, you are ready to place order";
    }


    @GetMapping("/{id}")

    public Optional<Order> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable Long id, @RequestBody Order order) {
        return orderService.updateOrder(id, order);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }

    @GetMapping("/test/{farmerId}/{dealerId}/{cropId}")
    public Map<String, Object> testFeignClients(
            @PathVariable String farmerId,
            @PathVariable String dealerId,
            @PathVariable String cropId) {

        Map<String, Object> response = new HashMap<>();
        response.put("farmer", orderService.getFarmerById(farmerId));
        response.put("dealer", orderService.getDealerById(dealerId));
        response.put("crop", orderService.getCropById(cropId));

        return response;
    }


}
