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
public class Carriers {
	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
    public int id;
	
	public String origin;
	
	public String destination;
	
	public String truckId;
	
	public int cost;
	
	public int time;
	
	public int capacity;
	
	@Enumerated(EnumType.STRING)
    public LoadType loadType;

    public enum LoadType {
        LCL,
        FCL
    }
}
