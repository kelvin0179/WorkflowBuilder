package com.example.workflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.workflow.model.Workflow;

public interface WorkflowRepository extends JpaRepository<Workflow, Integer>{
	
}
