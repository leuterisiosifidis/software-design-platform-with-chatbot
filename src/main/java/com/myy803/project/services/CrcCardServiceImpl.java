package com.myy803.project.services;

import com.myy803.project.domain.CrcCard;
import com.myy803.project.domain.Project;
import com.myy803.project.repositories.CrcCardRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CrcCardServiceImpl implements CrcCardService {
	
	private final CrcCardRepository crcCardRepository;
	
	public CrcCardServiceImpl(CrcCardRepository crcCardRepository) {
		this.crcCardRepository = crcCardRepository;
	}
	
	@Override
	public List<CrcCard> findByProject(Project project) {
		return crcCardRepository.findByProject(project);
	}
	
	@Override
	public CrcCard findById(int id) {
		return crcCardRepository.findById(id).orElse(null);
	}
	
	@Override
	public void saveCrcCard(CrcCard crcCard) {
		crcCardRepository.save(crcCard);
	}
	
	@Override
	public void deleteCrcCard(int id) {
		crcCardRepository.deleteById(id);
	}
}
