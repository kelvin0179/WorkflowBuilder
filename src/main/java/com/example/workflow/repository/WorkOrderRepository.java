package com.example.workflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.workflow.model.WorkOrder;

public interface WorkOrderRepository extends JpaRepository<WorkOrder,Integer> {
    
}
