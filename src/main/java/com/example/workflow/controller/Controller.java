package com.example.workflow.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.workflow.model.Carrier;
import com.example.workflow.model.Carrier_;
import com.example.workflow.model.Edges;
import com.example.workflow.model.EdgesComposite;
import com.example.workflow.model.Workflow;
import com.example.workflow.repository.CarrierRepository;
import com.example.workflow.repository.WorkflowRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*")
public class Controller {
	
	@Autowired
	CarrierRepository carrierRepository;
	
	@Autowired
	WorkflowRepository workflowRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@GetMapping("/")
	public List<Carrier> getCarrier(){
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Carrier> criteriaQuery=cb.createQuery(Carrier.class);
		Root<Carrier> root=criteriaQuery.from(Carrier.class);
		criteriaQuery.select(root).where(cb.equal(root.get(Carrier_.cost), 1));
		
		List<Carrier> carriers=entityManager.createQuery(criteriaQuery).getResultList();
		return carriers;
	}
	
	@PostMapping("/")
	public void postCarrier(@RequestBody Carrier carrier){
		carrierRepository.save(carrier);
	}
	
	@PostMapping("/work")
	@Transactional
	public void postWorkflow() {
	    Workflow workflow = new Workflow();
	    workflow=workflowRepository.save(workflow).toBuilder().build();

	    System.out.println(workflow.getId());
	    
	    Edges edges = new Edges();
	    EdgesComposite composite = new EdgesComposite();
	    composite.setId("1");
	    edges.setCompositeId(composite);

	    entityManager.persist(edges);
//		System.out.println(workflow);
//		entityManager.persist(workflow);
	}
	
	@GetMapping("/show")
	public List<Workflow> show(){
		return workflowRepository.findById(4).stream().collect(Collectors.toList());
	}

}
