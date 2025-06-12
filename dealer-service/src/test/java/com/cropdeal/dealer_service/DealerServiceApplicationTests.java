package com.cropdeal.dealer_service;


import com.cropdeal.dealer_service.model.Dealer;
import com.cropdeal.dealer_service.repository.DealerRepository;
import com.cropdeal.dealer_service.service.DealerService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DealerServiceApplicationTests {

	@Mock
	private DealerRepository dealerRepository;

	@InjectMocks
	private DealerService dealerService;

	private Dealer sampleDealer;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		sampleDealer = new Dealer(1L, "Amit", "amit@example.com", "9876543210", "Delhi");
	}

	@Test
	public void testAddDealer() {
		when(dealerRepository.save(sampleDealer)).thenReturn(sampleDealer);

		Dealer result = dealerService.addDealer(sampleDealer);

		assertEquals("Amit", result.getName());
		verify(dealerRepository, times(1)).save(sampleDealer);
	}

	@Test
	public void testGetAllDealers() {
		List<Dealer> dealers = List.of(sampleDealer);
		when(dealerRepository.findAll()).thenReturn(dealers);

		List<Dealer> result = dealerService.getAllDealers();

		assertEquals(1, result.size());
		assertEquals("Amit", result.get(0).getName());
	}

	@Test
	public void testGetDealerById_Found() {
		when(dealerRepository.findById(1L)).thenReturn(Optional.of(sampleDealer));

		Dealer result = dealerService.getDealerById(1L);

		assertNotNull(result);
		assertEquals("Amit", result.getName());
	}

	@Test
	public void testGetDealerById_NotFound() {
		when(dealerRepository.findById(2L)).thenReturn(Optional.empty());

		assertThrows(EntityNotFoundException.class, () -> dealerService.getDealerById(2L));
	}

	@Test
	public void testUpdateDealer() {
		Dealer updated = new Dealer(null, "Amit Updated", "amit@example.com", "9876543210", "Mumbai");
		Dealer saved = new Dealer(1L, "Amit Updated", "amit@example.com", "9876543210", "Mumbai");

		when(dealerRepository.findById(1L)).thenReturn(Optional.of(sampleDealer));
		when(dealerRepository.save(any(Dealer.class))).thenReturn(saved);

		Dealer result = dealerService.updateDealer(1L, updated);

		assertEquals("Amit Updated", result.getName());
		assertEquals("Mumbai", result.getLocation());
	}

	@Test
	public void testDeleteDealer() {
		doNothing().when(dealerRepository).deleteById(1L);

		dealerService.deleteDealer(1L);

		verify(dealerRepository, times(1)).deleteById(1L);
	}
}
