package com.cropdeal.cropservice;


import com.cropdeal.cropservice.dto.FarmerDTO;
import com.cropdeal.cropservice.feign.FarmerClient;
import com.cropdeal.cropservice.model.Crop;
import com.cropdeal.cropservice.repository.CropRepository;
import com.cropdeal.cropservice.service.CropServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CropServiceApplicationTests {

	@Mock
	private CropRepository cropRepository;

	@Mock
	private FarmerClient farmerClient;

	@InjectMocks
	private CropServiceImpl cropService;

	private Crop sampleCrop;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		sampleCrop = new Crop(1L, "Wheat", "Grain", 100.0, 2000.0, "Punjab", "Available", 10L);
	}

	@Test
	public void testAddCrop_WithValidFarmer() {
		FarmerDTO farmerDTO = new FarmerDTO();
		farmerDTO.setId(10L);
		farmerDTO.setName("Ravi");

		when(farmerClient.getFarmerById(10L)).thenReturn(ResponseEntity.ok(farmerDTO));
		when(cropRepository.save(sampleCrop)).thenReturn(sampleCrop);

		Crop result = cropService.addCrop(sampleCrop);

		assertNotNull(result);
		assertEquals("Wheat", result.getName());
		verify(cropRepository, times(1)).save(sampleCrop);
	}

	@Test
	public void testAddCrop_WithInvalidFarmer() {
		when(farmerClient.getFarmerById(10L)).thenReturn(ResponseEntity.ok(null));

		RuntimeException exception = assertThrows(RuntimeException.class, () -> cropService.addCrop(sampleCrop));
		assertEquals("Farmer not found with ID: 10", exception.getMessage());
	}

	@Test
	public void testGetAllCrops() {
		List<Crop> crops = List.of(sampleCrop);
		when(cropRepository.findAll()).thenReturn(crops);

		List<Crop> result = cropService.getAllCrops();

		assertEquals(1, result.size());
		assertEquals("Wheat", result.get(0).getName());
	}

	@Test
	public void testGetCropById_Found() {
		when(cropRepository.findById(1L)).thenReturn(Optional.of(sampleCrop));

		Crop result = cropService.getCropById(1L);

		assertNotNull(result);
		assertEquals("Wheat", result.getName());
	}

	@Test
	public void testGetCropById_NotFound() {
		when(cropRepository.findById(2L)).thenReturn(Optional.empty());

		RuntimeException exception = assertThrows(RuntimeException.class, () -> cropService.getCropById(2L));
		assertEquals("Crop not found with ID: 2", exception.getMessage());
	}

	@Test
	public void testUpdateCrop() {
		Crop updated = new Crop(null, "Rice", "Grain", 150.0, 2500.0, "Haryana", "Available", 10L);
		Crop saved = new Crop(1L, "Rice", "Grain", 150.0, 2500.0, "Haryana", "Available", 10L);

		when(cropRepository.findById(1L)).thenReturn(Optional.of(sampleCrop));
		when(cropRepository.save(any(Crop.class))).thenReturn(saved);

		Crop result = cropService.updateCrop(1L, updated);

		assertEquals("Rice", result.getName());
		assertEquals(150.0, result.getQuantity());
	}

	@Test
	public void testDeleteCrop_Found() {
		when(cropRepository.existsById(1L)).thenReturn(true);
		doNothing().when(cropRepository).deleteById(1L);

		cropService.deleteCrop(1L);

		verify(cropRepository, times(1)).deleteById(1L);
	}

	@Test
	public void testDeleteCrop_NotFound() {
		when(cropRepository.existsById(2L)).thenReturn(false);

		RuntimeException exception = assertThrows(RuntimeException.class, () -> cropService.deleteCrop(2L));
		assertEquals("Crop not found with ID: 2", exception.getMessage());
	}
}
