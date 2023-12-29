package com.example.workflow.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
public class Nodes {
	@EmbeddedId
	private NodesComposite compositeId;
	
	private List<String> nodeValues;
	
	private String selectedValue;
	
	private boolean disabled;
	
	private int indexValue;

	private String label;

	private int height;
	private int width;

	private float x;
	private float y;
	
	@ManyToOne
	@MapsId("workflowId")
	@JsonIgnore
	@JoinColumn(name="workflow_id")
	private Workflow workflow;
	
}
