package com.cropdeal.farmer_service.repository;
import com.cropdeal.farmer_service.model.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;
public interface FarmerRepository extends JpaRepository<Farmer,Long>{

}
