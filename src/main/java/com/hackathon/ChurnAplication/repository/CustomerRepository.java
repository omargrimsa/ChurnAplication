package com.hackathon.ChurnAplication.repository;

import com.hackathon.ChurnAplication.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// La interface extiende de JpaRepository y los parámetros son <Entidad a gestionar, Tipo de dato del ID de la Entidad>
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Método para buscar clientes por su ID de negocio (ej. "CUST-001") en lugar del ID numérico
    Optional<Customer> findByExternalId(String externalId);
}
