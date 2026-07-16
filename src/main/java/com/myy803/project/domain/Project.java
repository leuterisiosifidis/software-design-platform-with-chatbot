package com.myy803.project.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "projects")
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private String name;

	@Column
	private String description;

	// Many projects can belong to one user.
	// This creates an "owner_id" foreign key column in the projects table.
	@ManyToOne
	@JoinColumn(name = "owner_id", nullable = false)
	private User owner;

	public Project() {}
	
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

}
