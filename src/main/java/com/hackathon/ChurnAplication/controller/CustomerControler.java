package com.hackathon.ChurnAplication.controller;


import com.hackathon.ChurnAplication.dto.CustomerCreateDTO;
import com.hackathon.ChurnAplication.dto.CustomerDetailDTO;
import com.hackathon.ChurnAplication.service.ICustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// @RestController: Indica que esta clase maneja peticiones HTTP (GET, POST, etc.) y devuelve datos (JSON).
@RestController
// @RequestMapping: Define la dirección base. Todas las rutas de aquí empezarán con /api/customers
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerControler {

    // Inyectamos la interfaz del servicio, no la implementación directa (buenas prácticas de desacoplamiento).
    private final ICustomerService customerService;


    // @PostMapping: Indica que este método responde a peticiones tipo POST (usadas para crear cosas).
    // La URL completa será: POST http://localhost:8080/api/customers
    @PostMapping
    public ResponseEntity<CustomerDetailDTO> createCustomer(@Valid @RequestBody CustomerCreateDTO createDTO) {

        // @Valid: Activa las validaciones que pusimos en el DTO (como @NotBlank).
        // @RequestBody: Le dice a Spring que tome el JSON que envía el usuario y lo convierta en el objeto Java 'createDTO'.

        CustomerDetailDTO newCustomer = customerService.createCustomer(createDTO);

        // Devolvemos el objeto creado y un código de estado HTTP 201 (CREATED), que es el estándar para creaciones exitosas.
        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
    }




}
