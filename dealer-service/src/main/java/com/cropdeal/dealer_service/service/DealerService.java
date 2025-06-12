package com.cropdeal.dealer_service.service;

import com.cropdeal.dealer_service.model.Dealer;
import com.cropdeal.dealer_service.repository.DealerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DealerService {

    private static final Logger logger = LoggerFactory.getLogger(DealerService.class);

    @Autowired
    private DealerRepository repository;

    public Dealer addDealer(Dealer dealer){
        logger.info("Adding dealer: {}", dealer);
        return repository.save(dealer);
    }

    public List<Dealer> getAllDealers(){
        logger.info("Fetching all dealers");
        return repository.findAll();
    }

    public Dealer getDealerById(Long id){
        logger.info("Fetching dealer with ID: {}", id);
        return repository.findById(id).orElseThrow(() -> {
            logger.warn("Dealer not found with ID: {}", id);
            return new EntityNotFoundException("Dealer not found with id " + id);
        });
    }

    public Dealer updateDealer(Long id, Dealer dealer){
        logger.info("Updating dealer with ID: {}", id);
        Dealer existing = getDealerById(id);
        existing.setName(dealer.getName());
        existing.setEmail(dealer.getEmail());
        existing.setPhone(dealer.getPhone());
        existing.setLocation(dealer.getLocation());
        Dealer updated = repository.save(existing);
        logger.debug("Updated dealer: {}", updated);
        return updated;
    }

    public void deleteDealer(Long id){
        logger.info("Deleting dealer with ID: {}", id);
        repository.deleteById(id);
        logger.info("Dealer with ID {} deleted successfully", id);
    }
}
