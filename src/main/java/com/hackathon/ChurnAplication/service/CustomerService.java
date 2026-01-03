package com.hackathon.ChurnAplication.service;

import com.hackathon.ChurnAplication.dto.CustomerCreateDTO;
import com.hackathon.ChurnAplication.dto.CustomerDetailDTO;
import com.hackathon.ChurnAplication.model.Customer;
import com.hackathon.ChurnAplication.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {
    /* Función de la clase:
       - Aquí es donde ocurre la lógica real
       - Tomamos el DTO, lo convertimos en Entidad, lo guardamos y devolvemos el resultado.
       */
    private final CustomerRepository customerRepository;

    @Override
    public CustomerDetailDTO createCustomer(CustomerCreateDTO dto) {

        // 1. Validación de Negocio: Verificar si ya existe un cliente con ese ID externo.
        if (customerRepository.existsByExternalId(dto.getExternalId())) {
            throw new RuntimeException("Ya existe un cliente con el ID externo: " + dto.getExternalId());
        }
        // 2. Mapeo (Convertir DTO a Entidad):
        // Pasamos los datos del objeto ligero (DTO) a la Entidad que se guarda en BD.
        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setExternalId(dto.getExternalId());

        // Asignamos la fecha de creación automática (auditoría básica).
        customer.setCreatedAt(LocalDateTime.now());

        // 3. Persistencia: Guardamos en la base de datos (H2 en este caso).
        // El método .save() devuelve la entidad ya guardada (con su ID numérico generado).
        Customer savedCustomer = customerRepository.save(customer);

        // 4. Mapeo de Salida (Convertir Entidad a DTO):
        // Preparamos la respuesta para el usuario.
        CustomerDetailDTO responseDetailDto = new CustomerDetailDTO();
        responseDetailDto.setId(savedCustomer.getId());
        responseDetailDto.setExternalId(savedCustomer.getExternalId());
        responseDetailDto.setName(savedCustomer.getName());
        responseDetailDto.setCreatedAt(savedCustomer.getCreatedAt());

        // Inicializamos la lista de historial vacía, porque es un cliente nuevo.
        responseDetailDto.setHistory(new ArrayList<>());

        return responseDetailDto;





    }
}
