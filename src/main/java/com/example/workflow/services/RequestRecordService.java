package com.example.workflow.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.workflow.DTO.RequestRecordDTO;
import com.example.workflow.DTO.RequestRecordIdMap;
import com.example.workflow.DTO.WorkOrderPageDTO;
import com.example.workflow.comparator.CarriersComparator;
import com.example.workflow.model.Carrier;
import com.example.workflow.model.Carrier_;
import com.example.workflow.model.Carriers;
import com.example.workflow.model.Carriers_;
import com.example.workflow.model.Edges;
import com.example.workflow.model.Nodes;
import com.example.workflow.model.RequestRecord;
import com.example.workflow.model.WorkOrder;
import com.example.workflow.model.Workflow;
import com.example.workflow.repository.CarrierRepository;
import com.example.workflow.repository.CarriersRepository;
import com.example.workflow.repository.RequestRecordRepository;
import com.example.workflow.repository.WorkOrderRepository;
import com.example.workflow.repository.WorkflowRepository;

import ch.qos.logback.core.pattern.parser.Node;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class RequestRecordService {
	
	@Autowired
	private WorkflowRepository workflowRepository;

	@Autowired
	private WorkOrderRepository workOrderRepository; 

	@Autowired
	private RequestRecordRepository requestRecordRepository;

	@PersistenceContext
	private EntityManager entityManager;

	public String searchOperationType(List<Nodes> nodes) {
		for(Nodes node: nodes){
			if(node.getSelectedValue().equals("Send Request by Priority Search") || node.getSelectedValue().equals("BroadCast Request to All")){
				return node.getSelectedValue();
			}
		}
		return "No Selected Value";
	}
	public List<String> getSortOrder(List<Nodes> nodes,List<Edges> edges)  {
		List<String> sortOrder = new ArrayList<String>();
		String nodeId="0";
		boolean continueArray=true;
		while(continueArray){
			for(Edges edge : edges){
				if(edge.getSource().equals(nodeId)){
					nodeId=edge.getTarget();
					break;
				}
			}
			for(Nodes node : nodes){
				if(node.getCompositeId().getId().equals(nodeId)){
					if(node.getSelectedValue().equals("Search For Carriers")){
						continueArray=false;
						break;
					}
					else{
						sortOrder.add(node.getSelectedValue());
						break;
					}
				}
			}
		}
		return sortOrder;
	}
	public boolean cycleForPriorityRequest(List<Nodes> nodes,List<Edges> edges){
		String nodeIdProcess="",nodeIdReject="";
		for(Nodes node : nodes){
			if(node.getSelectedValue().equals("Send Request by Priority Search")){
				nodeIdProcess=node.getCompositeId().getId();
			}
			if(node.getSelectedValue().equals("Reject")){
				nodeIdReject=node.getCompositeId().getId();
			}
		}
		int numEdges=0;
		for(Edges edge : edges){
			if(edge.getSource().equals(nodeIdReject) && edge.getTarget().equals(nodeIdProcess)){
				numEdges++;
			}
			if(edge.getSource().equals(nodeIdProcess) && edge.getTarget().equals(nodeIdReject)){
				numEdges++;
			}
		}
		return numEdges==2;
	}
	public List<Carriers> dynamicParamsQuery(RequestRecordDTO requestRecordDTO){
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Carriers> criteriaQuery=cb.createQuery(Carriers.class);
		Root<Carriers> root=criteriaQuery.from(Carriers.class);

		if(requestRecordDTO.getCost()!=null){
			criteriaQuery.select(root).where(cb.lessThanOrEqualTo(root.get(Carriers_.cost), requestRecordDTO.getCost()));
		}
		if(requestRecordDTO.getTime()!=null){
			criteriaQuery.select(root).where(cb.lessThanOrEqualTo(root.get(Carriers_.time), requestRecordDTO.getTime()));
		}
		if(requestRecordDTO.getCapacity()!=null){
			criteriaQuery.select(root).where(cb.lessThanOrEqualTo(root.get(Carriers_.capacity), requestRecordDTO.getCapacity()));
		}
		criteriaQuery.select(root).where(cb.equal(root.get(Carriers_.origin), requestRecordDTO.getOrigin()));
		criteriaQuery.select(root).where(cb.equal(root.get(Carriers_.destination), requestRecordDTO.getDestination()));
		return entityManager.createQuery(criteriaQuery).getResultList();
	}

	public WorkOrder createRequest(RequestRecordDTO requestRecordDTO) {
		Workflow workflow=workflowRepository.findById(requestRecordDTO.getWorkflowId()).get().toBuilder().build();

		List<Carriers> carriers=dynamicParamsQuery(requestRecordDTO);

		List<Nodes> nodes = workflow.getNodes();
		List<Edges> edges = workflow.getEdges();
		List<String> sortOrder=getSortOrder(nodes, edges);

		String opertionType=searchOperationType(nodes);

		WorkOrder workOrder = new WorkOrder();
		workOrder.setOrigin(requestRecordDTO.getOrigin());
		workOrder.setDestination(requestRecordDTO.getDestination());
		workOrder.status=WorkOrder.Status.Pending;

		if(carriers.isEmpty()){
			workOrder.status=WorkOrder.Status.Rejected;
			return workOrder;
		}

		workOrder=workOrderRepository.save(workOrder).toBuilder().build();

		switch (opertionType) {
			case "BroadCast Request to All":{
				for(Carriers car: carriers){
					RequestRecord requestRecord = RequestRecord.builder()
													.workOrder(workOrder)
													.carriers(car.toBuilder().build())
													.workflow(workflow.toBuilder().build())
													.status(RequestRecord.Status.Assigned)
													.cost(requestRecordDTO.getCost())
													.time(requestRecordDTO.getTime())
													.capacity(requestRecordDTO.getCapacity())
													.build();
					requestRecordRepository.save(requestRecord);
				}
				break;
			}
			case "Send Request by Priority Search":{
				carriers.sort(new CarriersComparator(sortOrder));
				RequestRecord requestRecord = RequestRecord.builder()
												.carriers(carriers.get(0).toBuilder().build())
												.workOrder(workOrder)
												.workflow(workflow)
												.status(RequestRecord.Status.Assigned)
												.cost(requestRecordDTO.getCost())
												.time(requestRecordDTO.getTime())
												.capacity(requestRecordDTO.getCapacity())
												.build();
				requestRecordRepository.save(requestRecord);
				break;
			}
			default:
				break;
		}
		return workOrder;
	}

	public List<RequestRecord> getRequests(){
		return requestRecordRepository.findAll();
	}

	public void editRequest(RequestRecordIdMap requestRecordIdMap){
		RequestRecord requestRecord=requestRecordRepository.findByWorkflowIdAndWorkOrderIdAndCarriersId(
				requestRecordIdMap.getWorkflowId(),
				requestRecordIdMap.getWorkOrderId(),
				requestRecordIdMap.getCarriersId())
				.get(0)
				.toBuilder()
				.build();
		requestRecord.status=requestRecordIdMap.getStatus();
		requestRecordRepository.save(requestRecord);
		String operationType = searchOperationType(requestRecord.getWorkflow().getNodes());

		WorkOrder workOrder = new WorkOrder();
		workOrder=requestRecord.getWorkOrder().toBuilder().build();

		if(requestRecord.status==RequestRecord.Status.Accepted){
			requestRecordRepository.deleteByWorkOrder(workOrder);
			workOrder.setStatus(WorkOrder.Status.Accepted);
			workOrderRepository.save(workOrder);
			return;
		}
		switch (operationType) {
			case "BroadCast Request to All":{
				if(requestRecordRepository.findByWorkOrderAndStatus(requestRecord.getWorkOrder(),RequestRecord.Status.Assigned).isEmpty()){
					requestRecordRepository.deleteByWorkOrder(workOrder);
					workOrder.setStatus(WorkOrder.Status.Rejected);
					workOrderRepository.save(workOrder);
				}		
				break;
			}
			case "Send Request by Priority Search":{
				if(!cycleForPriorityRequest(requestRecord.getWorkflow().getNodes(), requestRecord.getWorkflow().getEdges())){
					requestRecordRepository.deleteByWorkOrder(workOrder);
					workOrder.setStatus(WorkOrder.Status.Rejected);
					workOrderRepository.save(workOrder);
					break;
				}
				List<RequestRecord> rejectedRequests = requestRecordRepository.findByWorkOrderAndStatus(workOrder, RequestRecord.Status.Rejected);
				RequestRecordDTO requestRecordDTO = RequestRecordDTO.builder()
													.origin(workOrder.getOrigin())
													.destination(workOrder.getDestination())
													.cost(requestRecord.getCost())
													.time(requestRecord.getTime())
													.capacity(requestRecord.getCapacity())
													.workflowId(requestRecordIdMap.getWorkflowId())
													.build();
				List<Carriers> carriers = dynamicParamsQuery(requestRecordDTO);
				List<Carriers> rejectedCarriers=new ArrayList<Carriers>();
				for(RequestRecord rejectedRequest : rejectedRequests){
					rejectedCarriers.add(rejectedRequest.getCarriers().toBuilder().build());
				}
				carriers.removeAll(rejectedCarriers);
				if(carriers.isEmpty()){
					requestRecordRepository.deleteByWorkOrder(workOrder);
					workOrder.setStatus(WorkOrder.Status.Rejected);
					workOrderRepository.save(workOrder);
					break;
				}
				carriers.sort(new CarriersComparator(getSortOrder(requestRecord.getWorkflow().getNodes(), requestRecord.getWorkflow().getEdges())));
				
				RequestRecord newRequestRecord = RequestRecord.builder()
												.carriers(carriers.get(0).toBuilder().build())
												.workOrder(workOrder)
												.workflow(requestRecord.getWorkflow().toBuilder().build())
												.status(RequestRecord.Status.Assigned)
												.cost(requestRecord.getCost())
												.time(requestRecord.getTime())
												.capacity(requestRecord.getCapacity())
												.build();
				requestRecordRepository.save(newRequestRecord);
				break;
			}
			default:
				break;
		}
	}
	public List<WorkOrderPageDTO> getWorkflowDataToWorkOrderPage(){
		List<Workflow> workflows=workflowRepository.findAll();
		List<WorkOrderPageDTO> result=new ArrayList<WorkOrderPageDTO>();
		for(Workflow workflow : workflows){
			List<String> nodeValues=getSortOrder(workflow.getNodes(), workflow.getEdges());
			result.add(WorkOrderPageDTO.builder()
					.id(workflow.getId())
					.name(workflow.getName())
					.nodeValues(nodeValues)
					.build());
		}
		return result;
	}
}
