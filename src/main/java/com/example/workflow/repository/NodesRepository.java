package com.example.workflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.workflow.model.Nodes;
import com.example.workflow.model.NodesComposite;

public interface NodesRepository extends JpaRepository<Nodes, NodesComposite>{
	
	void deleteByCompositeId_IdAndCompositeId_WorkflowId(String id, int workflowId);
	
	void deleteByCompositeId_WorkflowId(int workflowId);
}
