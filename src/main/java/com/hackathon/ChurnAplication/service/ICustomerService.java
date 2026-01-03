package com.hackathon.ChurnAplication.service;


import com.hackathon.ChurnAplication.dto.CustomerCreateDTO;
import com.hackathon.ChurnAplication.dto.CustomerDetailDTO;

public interface ICustomerService {
    // Método que recibe el DTO de creación y devuelve el DTO con los detalles del cliente ya guardado.
    CustomerDetailDTO createCustomer(CustomerCreateDTO customerCreateDto);
}
