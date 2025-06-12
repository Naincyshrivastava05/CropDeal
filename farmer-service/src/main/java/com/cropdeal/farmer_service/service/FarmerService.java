package com.cropdeal.farmer_service.service;

import com.cropdeal.farmer_service.model.Farmer;
import com.cropdeal.farmer_service.repository.FarmerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FarmerService {

    private static final Logger logger = LoggerFactory.getLogger(FarmerService.class);

    @Autowired
    private FarmerRepository repo;

    public List<Farmer> getAllFarmers() {
        logger.info("Fetching all farmers from repository");
        return repo.findAll();
    }

    public Farmer getFarmerById(Long id) {
        logger.info("Fetching farmer by ID: {}", id);
        return repo.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Farmer not found with ID: {}", id);
                    return new EntityNotFoundException("Farmer not found with id " + id);
                });
    }

    public Farmer addFarmer(Farmer farmer) {
        logger.info("Saving new farmer: {}", farmer.getName());
        Farmer saved = repo.save(farmer);
        logger.debug("Farmer saved: {}", saved);
        return saved;
    }

    public void deleteFarmer(Long id) {
        logger.info("Deleting farmer with ID: {}", id);
        repo.deleteById(id);
        logger.info("Farmer with ID {} deleted successfully", id);
    }

    public Farmer updateFarmer(Long id, Farmer updated) {
        logger.info("Updating farmer with ID: {}", id);
        updated.setId(id);
        Farmer saved = repo.save(updated);
        logger.debug("Updated farmer: {}", saved);
        return saved;
    }
}
