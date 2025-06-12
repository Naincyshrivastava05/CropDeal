package com.cropdeal.orderservice.service;

import com.cropdeal.orderservice.dto.CropDTO;
import com.cropdeal.orderservice.dto.DealerDTO;
import com.cropdeal.orderservice.dto.FarmerDTO;
import com.cropdeal.orderservice.entity.Order;
import com.cropdeal.orderservice.feign.CropClient;
import com.cropdeal.orderservice.feign.DealerClient;
import com.cropdeal.orderservice.feign.FarmerClient;
import com.cropdeal.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CropClient cropClient;

    @Autowired
    private FarmerClient farmerClient;

    @Autowired
    private DealerClient dealerClient;




        public Order updateStatus(Long orderId, String status) {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

            order.setStatus(status);
            System.out.println("Updating order " + orderId + " to status " + status);
            return orderRepository.save(order);
        }

   public void updatePaymentStatus(Long orderId, String paymentStatus){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        order.setPaymentStatus(paymentStatus);
        System.out.println("Updating order " + orderId + " to paymentStatus " + paymentStatus);
         orderRepository.save(order);
    }


    public FarmerDTO getFarmerById(String id) {
        return farmerClient.getFarmerById(id);
    }

    public DealerDTO getDealerById(String id) {
        return dealerClient.getDealerById(id);
    }

    public CropDTO getCropById(String id) {
        return cropClient.getCropById(id);
    }


    public Order createOrder(Order order) {
        // Validate crop
        CropDTO crop = cropClient.getCropById(order.getCropId());
        FarmerDTO farmer = farmerClient.getFarmerById(order.getFarmerId());
        DealerDTO dealer = dealerClient.getDealerById(order.getDealerId());

        if (crop == null) {
            throw new RuntimeException("Invalid crop ID: " + order.getCropId());
        }

        // Enrich order with crop details
        order.setCropName(crop.getName());
        order.setCropType(crop.getType());

        if(crop.getQuantity()< order.getQuantity()){
            throw new RuntimeException("Request quantitu is more than available");
        }
        order.setFarmerId(farmer.getId());
        order.setDealerId(dealer.getId());
        order.setCropName(crop.getName());
        order.setCropType(crop.getType());
        order.setStatus("Pending");
        String msg = "Dealer "+order.getDealerId()+" ordered Crop ID:" +order.getCropId()+"/n Crop Name:"+order.getCropName();
//        rabbitTemplate.convertAndSend("cropdeal-exchange", "order placed", msg);
        return orderRepository.save(order);

    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Order updateOrder(Long id, Order updatedOrder) {
        return orderRepository.findById(id).map(order -> {
            order.setCropId(updatedOrder.getCropId());
            order.setCropName(updatedOrder.getCropName());
            order.setCropType(updatedOrder.getCropType());
            order.setFarmerId(updatedOrder.getFarmerId());
            order.setDealerId(updatedOrder.getDealerId());
          order.setPrice(updatedOrder.getPrice());
            order.setQuantity(updatedOrder.getQuantity());
            order.setStatus(updatedOrder.getStatus());
            order.setOrderDate(updatedOrder.getOrderDate());
            order.setPaymentStatus(updatedOrder.getPaymentStatus());
            return orderRepository.save(order);
        }).orElseGet(() -> {
            updatedOrder.setOrderId(id);
            return orderRepository.save(updatedOrder);
        });
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
