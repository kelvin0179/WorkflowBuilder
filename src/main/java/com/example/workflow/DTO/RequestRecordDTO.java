package com.example.workflow.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class RequestRecordDTO {
    private String origin;
    private String destination;
    private Integer workflowId;
    private Integer cost;
    private Integer time;
    private Integer capacity;

}
