package com.example.workflow.controller;

import com.example.workflow.model.WorkOrder;
import com.example.workflow.services.WorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/workorders")
@CrossOrigin(origins = "*")
public class WorkOrderController {
	
	@Autowired
    private WorkOrderService workOrderService;


    @GetMapping
    public List<WorkOrder> getAllWorkOrders() {
        return workOrderService.getAllWorkOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkOrder> getWorkOrderById(@PathVariable int id) {
        Optional<WorkOrder> workOrder = workOrderService.getWorkOrderById(id);
        return workOrder.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<WorkOrder> createWorkOrder(@RequestBody WorkOrder workOrder) {
        WorkOrder createdWorkOrder = workOrderService.createWorkOrder(workOrder);
        return new ResponseEntity<>(createdWorkOrder, HttpStatus.CREATED);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<WorkOrder>> createWorkOrderBatch(@RequestBody List<WorkOrder> workOrderList) {
        List<WorkOrder> createdWorkOrders = workOrderService.createWorkOrderBatch(workOrderList);
        return new ResponseEntity<>(createdWorkOrders, HttpStatus.CREATED);
    }
}
