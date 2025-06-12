package com.cropdeal.dealer_service.controller;

import com.cropdeal.dealer_service.model.Dealer;
import com.cropdeal.dealer_service.service.DealerService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/dealers")
public class DealerController {

    private static final Logger logger = LoggerFactory.getLogger(DealerController.class);

    @Autowired
    private DealerService service;

    @PostMapping
    public ResponseEntity<Dealer> add(@Valid @RequestBody Dealer dealer) {
        logger.info("Received request to add dealer: {}", dealer);
        return ResponseEntity.ok(service.addDealer(dealer));
    }
        @GetMapping("/")
        public ResponseEntity<List<Dealer>> getAll() {
            logger.info("Received request to fetch all dealers");
            return ResponseEntity.ok(service.getAllDealers());
        }

        @GetMapping("/{id}")
        public ResponseEntity<Dealer> getById(@PathVariable Long id) {
            logger.info("Received request to fetch dealer with ID: {}", id);
            return ResponseEntity.ok(service.getDealerById(id));
        }

        @PutMapping("/{id}")
        public ResponseEntity<Dealer> update(@PathVariable Long id, @Valid @RequestBody Dealer dealer) {
            logger.info("Received request to update dealer with ID: {}", id);
            return ResponseEntity.ok(service.updateDealer(id, dealer));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<String> delete(@PathVariable Long id) {
            logger.info("Received request to delete dealer with ID: {}", id);
            service.deleteDealer(id);
            return ResponseEntity.ok("Dealer deleted successfully");
        }
    }
