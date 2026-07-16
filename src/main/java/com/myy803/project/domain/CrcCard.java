package com.myy803.project.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "crc_card")
public class CrcCard {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private String className;
	
	@Lob
	@Column
	private String responsibilities;
	
	@Lob
	@Column
	private String collaborations;
	
	@ManyToOne
	@JoinColumn(name = "project_id", nullable = false)
	private Project project;
	
	@ManyToMany
	@JoinTable(
		name = "crc_card_use_cases",
		joinColumns = @JoinColumn(name = "crc_card_id"),
		inverseJoinColumns = @JoinColumn(name = "use_case_id")
	)
	private List<UseCase> useCases = new ArrayList<>();
	
	public CrcCard() {}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id ) {
		this.id = id;
	}
	
	public String getClassName() {
		return className;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getResponsibilities() {
		return responsibilities;
	}
	
	public void setResponsibilities(String responsibilities) {
		this.responsibilities = responsibilities;
	}
	
	public String getCollaborations() {
		return collaborations;
	}
	
	public void setCollaborations(String collaborations) {
		this.collaborations = collaborations;
	}
	
	public Project getProject() {
		return project;
	}
	
	public void setProject(Project project) {
		this.project = project;
	}
	
	public List<UseCase> getUseCases() {
		return useCases;
	}
	
	public void setUseCases(List<UseCase> useCases) {
		this.useCases = useCases;
	}
}
