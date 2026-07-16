package com.myy803.project.services;

import com.myy803.project.domain.CrcCard;
import com.myy803.project.domain.Project;
import java.util.List;

public interface CrcCardService {
	
	List<CrcCard> findByProject(Project project);
	CrcCard findById(int id);
	void saveCrcCard(CrcCard crcCard);
	void deleteCrcCard(int id);
}
