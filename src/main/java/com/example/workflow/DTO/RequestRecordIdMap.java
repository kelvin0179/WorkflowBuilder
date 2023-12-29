package com.example.workflow.DTO;

import com.example.workflow.model.RequestRecord;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class RequestRecordIdMap {
	private int workflowId;
	private int workOrderId;
	private int carriersId;
	private RequestRecord.Status status;
}
