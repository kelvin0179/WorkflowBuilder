package com.example.workflow.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
public class RequestRecord {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "workflow_id", nullable = false)
    private Workflow workflow;

    @ManyToOne
    @JoinColumn(name = "work_order_id")
    private WorkOrder workOrder;

    @ManyToOne
    @JoinColumn(name = "carriers_id")
    private Carriers carriers;

    @Enumerated(EnumType.STRING)
    public Status status;

    public enum Status {
        Accepted,
        Rejected,
        Assigned
    }

    private Integer cost;

    private Integer time;

    private Integer capacity;

    private String loadType;
}
