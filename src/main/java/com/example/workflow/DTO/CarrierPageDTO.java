package com.example.workflow.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CarrierPageDTO {
	private Integer workOrderId;
	private Integer workflowId;
	private Integer carrierId;
	private String truckId;
	private String workflowName;
	private String origin;
	private String destination;
}
