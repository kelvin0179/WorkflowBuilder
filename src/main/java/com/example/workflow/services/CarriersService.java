package com.example.workflow.services;

import com.example.workflow.model.Carriers;
import com.example.workflow.repository.CarriersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarriersService {
	
	@Autowired
    private CarriersRepository carriersRepository;

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
}
