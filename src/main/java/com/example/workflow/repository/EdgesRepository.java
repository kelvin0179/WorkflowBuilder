package com.example.workflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.workflow.model.Edges;
import com.example.workflow.model.EdgesComposite;

public interface EdgesRepository extends JpaRepository<Edges, EdgesComposite>{
	
	void deleteByCompositeId_IdAndCompositeId_WorkflowId(String id, int workflowId);
	
	void deleteByCompositeId_WorkflowId(int workflowId);
}
