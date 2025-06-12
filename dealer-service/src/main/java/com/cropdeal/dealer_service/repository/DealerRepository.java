package com.cropdeal.dealer_service.repository;

import com.cropdeal.dealer_service.model.Dealer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealerRepository extends JpaRepository<Dealer, Long> {
}
