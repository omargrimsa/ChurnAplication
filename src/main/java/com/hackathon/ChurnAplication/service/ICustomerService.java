package com.hackathon.ChurnAplication.service;

import com.hackathon.ChurnAplication.dto.CustomerCreateDTO;
import com.hackathon.ChurnAplication.dto.CustomerDetailDTO;
import java.util.List;

public interface ICustomerService {
    // Método que recibe el DTO de creación y devuelve la lista de todos los clientes.
    List<CustomerDetailDTO> createCustomer(CustomerCreateDTO customerCreateDto);

    // Método para obtener todos los clientes.
    List<CustomerDetailDTO> getAllCustomers();

    // Nuevo método para buscar clientes por criterio (externalId o name) y término.
    List<CustomerDetailDTO> searchCustomers(String searchBy, String searchTerm);
}
