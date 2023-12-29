package com.example.workflow.controller;

import org.hibernate.boot.registry.classloading.spi.ClassLoaderService.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.workflow.model.Workflow;
import com.example.workflow.repository.WorkflowRepository;
import com.example.workflow.services.WorkflowService;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/workflow")
@CrossOrigin(origins = "*")
public class WorkflowController {
	@Autowired
	WorkflowService workflowService;
	
	@GetMapping("/{id}")
	public ResponseEntity<Workflow> getWorkflow(@PathVariable("id") Integer id){
		try {
			return ResponseEntity.ok(workflowService.getWorkflow(id).get());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
	
	@PostMapping("/")
	public void postWorkflow(@RequestBody Workflow workflow) {
		workflowService.postWorkflow(workflow);
	}

	@GetMapping("/getMaxNodeId/{id}")
	public ResponseEntity<Integer> getMethodName(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(workflowService.getMaxAvailableId(id).get());
	}
	
	
}
