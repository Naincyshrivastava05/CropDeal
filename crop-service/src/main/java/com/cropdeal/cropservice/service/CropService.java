package com.cropdeal.cropservice.service;

import com.cropdeal.cropservice.model.Crop;
import java.util.List;

public interface CropService {
    Crop addCrop(Crop crop);
    List<Crop> getAllCrops();
    Crop getCropById(Long id);
    void deleteCrop(Long id);
    Crop updateCrop(Long id, Crop crop);
}
