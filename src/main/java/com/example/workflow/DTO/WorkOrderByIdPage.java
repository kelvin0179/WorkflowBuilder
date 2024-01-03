package com.example.workflow.DTO;

import java.util.List;

import com.example.workflow.model.Carriers;
import com.example.workflow.model.RequestRecord;
import com.example.workflow.model.WorkOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class WorkOrderByIdPage {
	private String origin;	
	private String destination;
	private WorkOrder.Status status;
	private String workflowName;
	private Integer cost;
	private Integer time;
	private Integer capacity;
	private String loadType;
	private List<WorkOrderDetailsCarrierDTO> carriers;
}
