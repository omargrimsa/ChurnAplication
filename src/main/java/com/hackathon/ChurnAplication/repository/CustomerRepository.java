package com.hackathon.ChurnAplication.repository;

import com.hackathon.ChurnAplication.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// La interface extiende de JpaRepository y los parámetros son <Entidad a gestionar, Tipo de dato del ID de la Entidad>
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Método para buscar clientes por su ID de negocio (ej. "CUST-001") en lugar del ID numérico
    Optional<Customer> findByExternalId(String externalId);

    // Método adicional: usado pora verificar el externalId del cliente "si existe" (true/false), asi no se crea 2 veces
    // un registro con este externalId. Útil para validaciones antes de guardar.
    boolean existsByExternalId(String externalId);


}
