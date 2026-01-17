package com.hackathon.ChurnAplication.repository;

import com.hackathon.ChurnAplication.model.ModelInput;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ModelInputRepository extends JpaRepository<ModelInput, Long> {

    // Busca todos los inputs asociados a un cliente específico.
    // Spring Data JPA genera la query automáticamente basada en el nombre del método.
    List<ModelInput> findByCustomerId(Long customerId);

}
