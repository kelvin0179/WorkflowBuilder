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
public class WorkOrderDetailsCarrierDTO {
	private Integer carrierId;
	private String truckId;
	private Integer cost;
	private Integer time;
	private Integer capacity;
	private String loadType;
	private RequestRecord.Status status;
}
