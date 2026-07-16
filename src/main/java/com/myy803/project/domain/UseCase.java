package com.myy803.project.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "use_cases")
public class UseCase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private String name;
	
	@Column
	private String actors;
	
	@Lob
	@Column
	private String preconditions;
	
	@Lob
	@Column
	private String mainFlow;
	
	@Lob
	@Column
	private String alternativeFlow;
	
	@Lob
	@Column
	private String postconditions;
	
	@ManyToOne
	@JoinColumn(name = "project_id", nullable = false)
	private Project project;
	
	public UseCase() {}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getActors() {
		return actors;
	}
	
	public void setActors(String actors) {
		this.actors = actors;
	}
	
	public String getPreconditions() {
		return preconditions;
	}
	
	public void setPreconditions(String preconditions) {
		this.preconditions = preconditions;
	}
	
	public String getMainFlow() {
		return mainFlow;
	}
	
	public void setMainFlow(String mainFlow) {
		this.mainFlow = mainFlow;
	}
	
	public String getAlternativeFlow() {
		return alternativeFlow;
	}
	
	public void setAlternativeFlow(String alternativeFlow) {
		this.alternativeFlow = alternativeFlow;
	}
	
	public String getPostconditions() {
		return postconditions;
	}
	
	public void setPostconditions(String postconditions) {
		this.postconditions = postconditions;
	}
	
	public Project getProject() {
		return project;
	}
	
	public void setProject(Project project) {
		this.project = project;
	}
}
