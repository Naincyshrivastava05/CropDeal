package com.cropdeal.cropservice.service;

import com.cropdeal.cropservice.dto.FarmerDTO;
import com.cropdeal.cropservice.feign.FarmerClient;
import com.cropdeal.cropservice.model.Crop;
import com.cropdeal.cropservice.repository.CropRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CropServiceImpl implements CropService {

    private static final Logger logger = LoggerFactory.getLogger(CropServiceImpl.class);

    @Autowired
    private CropRepository repository;

    @Autowired
    private FarmerClient farmerClient;

    @Override
    public Crop addCrop(Crop crop) {
        Long farmerId = crop.getFarmerId();
        logger.info("Attempting to add crop for farmer ID: {}", farmerId);

        ResponseEntity<FarmerDTO> response = farmerClient.getFarmerById(farmerId);
        FarmerDTO farmer = response.getBody();

        if (farmer == null) {
            logger.warn("Farmer not found with ID: {}", farmerId);
            throw new RuntimeException("Farmer not found with ID: " + farmerId);
        }

        Crop savedCrop = repository.save(crop);
        logger.debug("Crop saved: {}", savedCrop);
        return savedCrop;
    }

    @Override
    public List<Crop> getAllCrops() {
        logger.info("Fetching all crops");
        return repository.findAll();
    }

    @Override
    public Crop getCropById(Long id) {
        logger.info("Fetching crop with ID: {}", id);
        return repository.findById(id).orElseThrow(() -> {
            logger.error("Crop not found with ID: {}", id);
            return new RuntimeException("Crop not found with ID: " + id);
        });
    }

    @Override
    public Crop updateCrop(Long id, Crop updatedCrop) {
        logger.info("Updating crop with ID: {}", id);
        Crop existing = repository.findById(id).orElseThrow(() -> {
            logger.error("Crop not found with ID: {}", id);
            return new RuntimeException("Crop not found with ID: " + id);
        });

        existing.setName(updatedCrop.getName());
        existing.setType(updatedCrop.getType());
        existing.setPrice(updatedCrop.getPrice());
        existing.setQuantity(updatedCrop.getQuantity());

        Crop saved = repository.save(existing);
        logger.debug("Updated crop: {}", saved);
        return saved;
    }

    @Override
    public void deleteCrop(Long id) {
        logger.info("Deleting crop with ID: {}", id);
        if (!repository.existsById(id)) {
            logger.error("Crop not found with ID: {}", id);
            throw new RuntimeException("Crop not found with ID: " + id);
        }
        repository.deleteById(id);
        logger.info("Crop with ID {} deleted successfully", id);
    }
}
