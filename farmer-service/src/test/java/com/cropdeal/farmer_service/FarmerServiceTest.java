package com.cropdeal.farmer_service.service;

import com.cropdeal.farmer_service.model.Farmer;
import com.cropdeal.farmer_service.repository.FarmerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FarmerServiceTest {

	@Mock
	private FarmerRepository farmerRepository;

	@InjectMocks
	private FarmerService farmerService;

	Farmer sampleFarmer;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		sampleFarmer = new Farmer(1L, "Ravi", "ravi@example.com", "9876543210", "Maharashtra");
	}

	@Test
	public void testGetAllFarmers() {
		List<Farmer> farmers = List.of(sampleFarmer);
		when(farmerRepository.findAll()).thenReturn(farmers);

		List<Farmer> result = farmerService.getAllFarmers();

		assertEquals(1, result.size());
		assertEquals("Ravi", result.get(0).getName());
	}

	@Test
	public void testGetFarmerById_Found() {
		when(farmerRepository.findById(1L)).thenReturn(Optional.of(sampleFarmer));

		Farmer result = farmerService.getFarmerById(1L);

		assertNotNull(result);
		assertEquals("Ravi", result.getName());
	}

	@Test
	public void testGetFarmerById_NotFound() {
		when(farmerRepository.findById(2L)).thenReturn(Optional.empty());

		assertThrows(EntityNotFoundException.class, () -> farmerService.getFarmerById(2L));
	}

	@Test
	public void testAddFarmer() {
		when(farmerRepository.save(sampleFarmer)).thenReturn(sampleFarmer);

		Farmer result = farmerService.addFarmer(sampleFarmer);

		assertEquals("Ravi", result.getName());
		verify(farmerRepository, times(1)).save(sampleFarmer);
	}

	@Test
	public void testDeleteFarmer() {
		doNothing().when(farmerRepository).deleteById(1L);

		farmerService.deleteFarmer(1L);

		verify(farmerRepository, times(1)).deleteById(1L);
	}

	@Test
	public void testUpdateFarmer() {
		Farmer updated = new Farmer(null, "Ravi Updated", "ravi@example.com", "9876543210", "Pune");
		Farmer saved = new Farmer(1L, "Ravi Updated", "ravi@example.com", "9876543210", "Pune");

		when(farmerRepository.save(any(Farmer.class))).thenReturn(saved);

		Farmer result = farmerService.updateFarmer(1L, updated);

		assertEquals("Ravi Updated", result.getName());
		assertEquals("Pune", result.getLocation());
	}
}
