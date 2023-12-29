package com.example.workflow.DTO;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class WorkOrderPageDTO {
	private int id;
	private String name;
	private List<String> nodeValues;
}
