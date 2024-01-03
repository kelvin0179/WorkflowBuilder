package com.example.workflow.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.workflow.DTO.WorkflowDTO;
import com.example.workflow.model.Edges;
import com.example.workflow.model.EdgesComposite;
import com.example.workflow.model.Nodes;
import com.example.workflow.model.NodesComposite;
import com.example.workflow.model.WorkOrder;
import com.example.workflow.model.Workflow;
import com.example.workflow.repository.EdgesRepository;
import com.example.workflow.repository.NodesRepository;
import com.example.workflow.repository.WorkflowRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class WorkflowService {
	
	@Autowired
	WorkflowRepository workflowRepository;
	
	@Autowired
	NodesRepository nodesRepository;
	
	@Autowired
	EdgesRepository edgesRepository;
	
	public Optional<Workflow> postWorkflow(Workflow workflow){
		Workflow newWorkflow=new Workflow();
		List<Nodes> nodes=new ArrayList<>(workflow.getNodes());
		List<Edges> edges=new ArrayList<Edges>(workflow.getEdges());
//		System.out.println(workflow);
		if(workflow.getId()==0){
			newWorkflow.setName(workflow.getName());
			newWorkflow=workflowRepository.save(newWorkflow).toBuilder().build();
		}
		else{
			// newWorkflow=workflow.toBuilder().build();
			newWorkflow=workflowRepository.findById(workflow.getId()).get();
			newWorkflow.setName(workflow.getName());
			newWorkflow=workflowRepository.save(newWorkflow).toBuilder().build();
		}
		nodesRepository.deleteByCompositeId_WorkflowId(newWorkflow.getId());
		edgesRepository.deleteByCompositeId_WorkflowId(newWorkflow.getId());
		for(int i=0;i<nodes.size();i++) {
			Nodes newNode=nodes.get(i).toBuilder().build();
			NodesComposite nodesComposite=newNode.getCompositeId().toBuilder().build();
			newNode.setWorkflow(newWorkflow);
			newNode.setCompositeId(nodesComposite);
			
			nodesRepository.save(newNode);
		}
		
		for (int i = 0; i < edges.size(); i++) {
            Edges newEdge = edges.get(i).toBuilder().build();
            EdgesComposite edgesComposite = newEdge.getCompositeId().toBuilder().build();
            newEdge.setWorkflow(newWorkflow);
            newEdge.setCompositeId(edgesComposite);

            edgesRepository.save(newEdge);
        }
		return Optional.of(newWorkflow);
	}
	
	public Optional<Workflow> getWorkflow(int id){
		return workflowRepository.findById(id);
	}

	public Optional<Integer> getMaxAvailableId(int id){
		List<Nodes> nodes=workflowRepository.findById(id).get().getNodes();
		int maxId=0;
		for(Nodes node : nodes){
			maxId=Math.max(maxId,Integer.parseInt(node.getCompositeId().getId()));
		}
		return Optional.of(maxId+1);
	}
	public List<WorkflowDTO> getForWorkflowPage(){
		List<Workflow> workflows=workflowRepository.findAll();
		List<WorkflowDTO> workflowDTOs=new ArrayList<WorkflowDTO>();
		for(Workflow workflow: workflows) {
			workflowDTOs.add(WorkflowDTO.builder()
					.id(workflow.getId())
					.name(workflow.getName())
					.build());
		}
		return workflowDTOs;
	}
}
