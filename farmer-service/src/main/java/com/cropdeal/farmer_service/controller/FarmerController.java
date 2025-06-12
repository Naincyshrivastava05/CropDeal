package com.cropdeal.farmer_service.controller;

import com.cropdeal.farmer_service.dto.FarmerDTO;
import com.cropdeal.farmer_service.model.Farmer;
import com.cropdeal.farmer_service.service.FarmerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/farmers")
public class FarmerController {

    private static final Logger logger = LoggerFactory.getLogger(FarmerController.class);

    @Autowired
    private FarmerService farmerService;

    @GetMapping("/")
    public String home() {
        logger.info("Accessed home endpoint");
        return "Welcome to Farmer Service";
    }

    @GetMapping
    public List<Farmer> getAllFarmers() {
        logger.info("Fetching all farmers");
        return farmerService.getAllFarmers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FarmerDTO> getFarmer(@PathVariable Long id) {
        logger.info("Fetching farmer with ID: {}", id);
        Farmer farmer = farmerService.getFarmerById(id);

        FarmerDTO dto = new FarmerDTO();
        dto.setName(farmer.getName());
        dto.setEmail(farmer.getEmail());
        dto.setLocation(farmer.getLocation());

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public Farmer createFarmer(@Valid @RequestBody Farmer farmer) {
        logger.info("Creating new farmer: {}", farmer.getName());
        return farmerService.addFarmer(farmer);
    }

    @PutMapping("/{id}")
    public Farmer updateFarmer(@PathVariable Long id, @Valid @RequestBody Farmer farmer) {
        logger.info("Updating farmer with ID: {}", id);
        return farmerService.updateFarmer(id, farmer);
    }

    @DeleteMapping("/{id}")
    public void deleteFarmer(@PathVariable Long id) {
        logger.info("Deleting farmer with ID: {}", id);
        farmerService.deleteFarmer(id);
    }
}
