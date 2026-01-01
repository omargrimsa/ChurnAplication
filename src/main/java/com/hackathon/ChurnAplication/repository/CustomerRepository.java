package com.hackathon.ChurnAplication.repository;

import com.hackathon.ChurnAplication.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

// La interface extiende de JpaRepository y los parametros son <Clase Mapeada, Tipo de dato del Id de la Clase Mapeada>
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
