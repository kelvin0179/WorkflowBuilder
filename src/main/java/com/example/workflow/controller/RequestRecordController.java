package com.example.workflow.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.workflow.DTO.RequestRecordDTO;
import com.example.workflow.DTO.RequestRecordIdMap;
import com.example.workflow.DTO.WorkOrderPageDTO;
import com.example.workflow.model.RequestRecord;
import com.example.workflow.model.WorkOrder;
import com.example.workflow.services.RequestRecordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/request")
public class RequestRecordController {

	@Autowired
	private RequestRecordService requestRecordService;

	@PostMapping
	public ResponseEntity<WorkOrder> postRequest(@RequestBody RequestRecordDTO record){
		return ResponseEntity.ok(requestRecordService.createRequest(record));
	}

	@GetMapping("/carrier")
	public ResponseEntity<List<RequestRecord>> getRequests() {
		return ResponseEntity.ok(requestRecordService.getRequests());
	}
	

	@PostMapping("/carrier")
	public void editRequest(@RequestBody RequestRecordIdMap requestRecordIdMap) {
		requestRecordService.editRequest(requestRecordIdMap);
	}
	
	@GetMapping("/workOrderPageData")
	public List<WorkOrderPageDTO> getMethodName() {
		return requestRecordService.getWorkflowDataToWorkOrderPage();
	}
	
}
