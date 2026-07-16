package com.myy803.project;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.myy803.project.domain.CrcCard;
import com.myy803.project.domain.Project;
import com.myy803.project.repositories.CrcCardRepository;
import com.myy803.project.services.CrcCardServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CrcCardServiceImplTest {

	@Mock
	private CrcCardRepository crcCardRepository;
	
	@InjectMocks
	private CrcCardServiceImpl crcCardService;
	
	@Test
	public void findByProject_ShouldReturnCrcCardList() {
		Project project = new Project();
		CrcCard c1 = new CrcCard();
		CrcCard c2 = new CrcCard();
		List<CrcCard> crcCards = Arrays.asList(c1, c2);
		
		when(crcCardRepository.findByProject(project)).thenReturn(crcCards);
		
		List<CrcCard> result = crcCardService.findByProject(project);
		
		Assertions.assertEquals(2, result.size());
		verify(crcCardRepository).findByProject(project);
	}
	
	@Test 
	public void findById_ShouldReturnCrcCard() {
		CrcCard crcCard = new CrcCard();
		
		when(crcCardRepository.findById(1)).thenReturn(Optional.of(crcCard));
		
		CrcCard result = crcCardService.findById(1);
		
		Assertions.assertNotNull(result);
		verify(crcCardRepository).findById(1);
	}
	
	@Test
	public void findById_WhenNotFound_ShouldReturnNull() {
		when(crcCardRepository.findById(1)).thenReturn(Optional.empty());
		
		CrcCard result = crcCardService.findById(1);
		
		Assertions.assertNull(result);
		verify(crcCardRepository).findById(1);
	}
	
	@Test
	public void saveCrcCard_ShouldCallRepositorySave() {
		CrcCard crcCard = new CrcCard();
		
		crcCardService.saveCrcCard(crcCard);
		
		verify(crcCardRepository).save(crcCard);
	}
	
	@Test
	public void deleteCrcCard_ShouldCallRepositoryDeleteById() {
		crcCardService.deleteCrcCard(1);
		
		verify(crcCardRepository).deleteById(1);
	}
}
