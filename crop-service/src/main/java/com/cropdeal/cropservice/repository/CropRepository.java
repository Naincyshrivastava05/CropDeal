package com.cropdeal.cropservice.repository;



import com.cropdeal.cropservice.model.Crop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CropRepository extends JpaRepository<Crop, Long> { }