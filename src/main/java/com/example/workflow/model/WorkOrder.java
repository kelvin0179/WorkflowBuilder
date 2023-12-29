package com.example.workflow.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
public class WorkOrder {
	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
    public int id;
	
	public String origin;
	
	public String destination;

    @Enumerated(EnumType.STRING)
    public Status status;

    public enum Status {
        Accepted,
        Rejected,
        Pending
    }
}
