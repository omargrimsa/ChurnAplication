package com.hackathon.ChurnAplication.service;

import com.hackathon.ChurnAplication.dto.CustomerCreateDTO;
import com.hackathon.ChurnAplication.dto.CustomerDetailDTO;
import com.hackathon.ChurnAplication.model.Customer;
import com.hackathon.ChurnAplication.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public List<CustomerDetailDTO> createCustomer(CustomerCreateDTO dto) {
        // 1. Validación y creación del nuevo cliente (lógica que ya tenías)
        if (customerRepository.existsByExternalId(dto.getExternalId())) {
            throw new RuntimeException("Ya existe un cliente con el ID externo: " + dto.getExternalId());
        }

        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setExternalId(dto.getExternalId());
        customer.setCreatedAt(LocalDateTime.now());

        customerRepository.save(customer);

        // 2. Después de guardar, obtén y devuelve la lista completa de clientes.
        return getAllCustomers();
    }

    @Override
    public List<CustomerDetailDTO> getAllCustomers() {
        // Obtenemos todas las entidades Customer de la base de datos.
        List<Customer> customers = customerRepository.findAll();

        // Mapeamos la lista de entidades a una lista de DTOs.
        return customers.stream()
                .map(this::mapToCustomerDetailDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerDetailDTO> searchCustomers(String searchBy, String searchTerm) {
        List<Customer> customers;
        if ("externalId".equalsIgnoreCase(searchBy)) {
            customers = customerRepository.findByExternalIdContainingIgnoreCase(searchTerm);
        } else if ("name".equalsIgnoreCase(searchBy)) {
            customers = customerRepository.findByNameContainingIgnoreCase(searchTerm);
        } else {
            // Si el criterio no es válido, devolvemos una lista vacía o todos los clientes.
            // Devolver una lista vacía es más seguro para evitar sobrecargar la BD.
            customers = new ArrayList<>();
        }

        return customers.stream()
                .map(this::mapToCustomerDetailDTO)
                .collect(Collectors.toList());
    }

    // Método de ayuda para convertir una entidad Customer a CustomerDetailDTO
    private CustomerDetailDTO mapToCustomerDetailDTO(Customer customer) {
        CustomerDetailDTO dto = new CustomerDetailDTO();
        dto.setId(customer.getId());
        dto.setExternalId(customer.getExternalId());
        dto.setName(customer.getName());
        dto.setCreatedAt(customer.getCreatedAt());
        // Aquí puedes agregar la lógica para cargar el historial si es necesario
        dto.setHistory(new ArrayList<>());
        return dto;
    }
}
