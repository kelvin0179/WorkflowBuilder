package com.example.workflow.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
public class Workflow {
	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private int id;

	private String name;
	
	@OneToMany(mappedBy = "workflow",fetch = FetchType.EAGER)
	private List<Nodes> nodes;
	
	@OneToMany(mappedBy = "workflow",fetch = FetchType.EAGER)
	private List<Edges> edges;
}
