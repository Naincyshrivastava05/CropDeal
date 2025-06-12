package com.cropdeal.cropservice.controller;

import com.cropdeal.cropservice.model.Crop;
import com.cropdeal.cropservice.service.CropService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/crops")
public class CropController {

    private static final Logger logger = LoggerFactory.getLogger(CropController.class);

    @Autowired
    private CropService cropService;

    @PostMapping
    public ResponseEntity<Crop> addCrop(@RequestBody Crop crop) {
        logger.info("Received request to add crop: {}", crop);
        return ResponseEntity.ok(cropService.addCrop(crop));
    }

    @GetMapping
    public ResponseEntity<List<Crop>> getAllCrops() {
        logger.info("Received request to fetch all crops");
        return ResponseEntity.ok(cropService.getAllCrops());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Crop> getCropById(@PathVariable Long id) {
        logger.info("Received request to fetch crop with ID: {}", id);
        return ResponseEntity.ok(cropService.getCropById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCrop(@PathVariable Long id) {
        logger.info("Received request to delete crop with ID: {}", id);
        cropService.deleteCrop(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public Crop updateCrop(@PathVariable Long id, @RequestBody Crop crop) {
        logger.info("Received request to update crop with ID: {}", id);
        return cropService.updateCrop(id, crop);
    }
}
