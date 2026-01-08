package com.hackathon.ChurnAplication.service;


import com.hackathon.ChurnAplication.dto.CustomerCreateDTO;
import com.hackathon.ChurnAplication.dto.CustomerDetailDTO;
import com.hackathon.ChurnAplication.dto.ModelInputDTO;

public interface ICustomerService {
    // Método que recibe el DTO de creación y devuelve el DTO con los detalles del cliente ya guardado.
    CustomerDetailDTO createCustomer(CustomerCreateDTO customerCreateDto);

    // Nuevo método para predecir churn
//    ModelInputDTO predictChurn(Long customerId, ModelInputDTO inputDTO);
}
