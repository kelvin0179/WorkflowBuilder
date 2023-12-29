package com.example.workflow.controller;

import com.example.workflow.model.Carriers;
import com.example.workflow.services.CarriersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/carriers")
@CrossOrigin(origins = "*")
public class CarriersController {

	@Autowired
    private CarriersService carriersService;


    @GetMapping
    public List<Carriers> getAllCarriers() {
        return carriersService.getAllCarriers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carriers> getCarriersById(@PathVariable int id) {
        Optional<Carriers> carriers = carriersService.getCarriersById(id);
        return carriers.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Carriers> createCarriers(@RequestBody Carriers carriers) {
        Carriers createdCarriers = carriersService.createCarriers(carriers);
        return new ResponseEntity<>(createdCarriers, HttpStatus.CREATED);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<Carriers>> createCarriersBatch(@RequestBody List<Carriers> carriersList) {
        List<Carriers> createdCarriers = carriersService.createCarriersBatch(carriersList);
        return new ResponseEntity<>(createdCarriers, HttpStatus.CREATED);
    }
}
