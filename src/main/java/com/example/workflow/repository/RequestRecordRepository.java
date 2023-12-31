package com.example.workflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.workflow.model.RequestRecord;
import com.example.workflow.model.WorkOrder;

public interface RequestRecordRepository extends JpaRepository<RequestRecord, Integer>{

    public List<RequestRecord> findByWorkOrderAndStatus(WorkOrder workOrder,RequestRecord.Status status);

    public void deleteByWorkOrder(WorkOrder workorder);

    public void deleteByWorkOrderAndStatus(WorkOrder workorder,RequestRecord.Status status);

    public List<RequestRecord> findByWorkOrder(WorkOrder workOrder);
    
    @Query("SELECT rr FROM RequestRecord rr " +
            "WHERE rr.workflow.id = :workflowId " +
            "AND rr.workOrder.id = :workOrderId " +
            "AND rr.carriers.id = :carriersId")
    List<RequestRecord> findByWorkflowIdAndWorkOrderIdAndCarriersId(
            @Param("workflowId") int workflowId,
            @Param("workOrderId") int workOrderId,
            @Param("carriersId") int carriersId
    );

    List<RequestRecord> findByStatus(RequestRecord.Status status);
}
