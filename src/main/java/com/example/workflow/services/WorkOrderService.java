package com.example.workflow.services;

import com.example.workflow.model.WorkOrder;
import com.example.workflow.repository.WorkOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkOrderService {
	
	@Autowired
    private WorkOrderRepository workOrderRepository;


    public List<WorkOrder> getAllWorkOrders() {
        return workOrderRepository.findAll();
    }

    public Optional<WorkOrder> getWorkOrderById(int id) {
        return workOrderRepository.findById(id);
    }

    public WorkOrder createWorkOrder(WorkOrder workOrder) {
        return workOrderRepository.save(workOrder);
    }

    public List<WorkOrder> createWorkOrderBatch(List<WorkOrder> workOrderList) {
        return workOrderRepository.saveAll(workOrderList);
    }
}
