package com.example.workflow.services;

import com.example.workflow.DTO.CarrierPageDTO;
import com.example.workflow.model.Carriers;
import com.example.workflow.model.RequestRecord;
import com.example.workflow.repository.CarriersRepository;
import com.example.workflow.repository.RequestRecordRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarriersService {
	
	@Autowired
    private CarriersRepository carriersRepository;

    @Autowired
    private RequestRecordRepository requestRecordRepository;

    public List<Carriers> getAllCarriers() {
        return carriersRepository.findAll();
    }

    public Optional<Carriers> getCarriersById(int id) {
        return carriersRepository.findById(id);
    }

    public Carriers createCarriers(Carriers carriers) {
        return carriersRepository.save(carriers);
    }

    public List<Carriers> createCarriersBatch(List<Carriers> carriersList) {
        return carriersRepository.saveAll(carriersList);
    }

    public List<CarrierPageDTO> getAllCarrierPageDTOs(){
        List<RequestRecord> requestRecords=requestRecordRepository.findByStatus(RequestRecord.Status.Assigned);
        List<CarrierPageDTO> carrierPageDTOs = new ArrayList<>();
        for(RequestRecord requestRecord : requestRecords){
            carrierPageDTOs.add(CarrierPageDTO.builder()
                                    .workOrderId(requestRecord.getWorkOrder().getId())
                                    .workflowId(requestRecord.getWorkflow().getId())
                                    .carrierId(requestRecord.getCarriers().getId())
                                    .workflowName(requestRecord.getWorkflow().getName())
                                    .origin(requestRecord.getWorkOrder().getOrigin())
                                    .destination(requestRecord.getWorkOrder().getDestination())
                                    .truckId(requestRecord.getCarriers().getTruckId())
                                    .build());
        }
        return carrierPageDTOs;
    }
}
