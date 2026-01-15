package com.hackathon.ChurnAplication.controller;

import com.hackathon.ChurnAplication.dto.CustomerCreateDTO;
import com.hackathon.ChurnAplication.dto.CustomerDetailDTO;
import com.hackathon.ChurnAplication.service.ICustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final ICustomerService customerService;

    @PostMapping
    public ResponseEntity<List<CustomerDetailDTO>> createCustomer(@Valid @RequestBody CustomerCreateDTO createDTO) {
        List<CustomerDetailDTO> allCustomers = customerService.createCustomer(createDTO);
        return new ResponseEntity<>(allCustomers, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CustomerDetailDTO>> getAllCustomers() {
        List<CustomerDetailDTO> allCustomers = customerService.getAllCustomers();
        return new ResponseEntity<>(allCustomers, HttpStatus.OK);
    }
}
