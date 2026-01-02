package com.hackathon.ChurnAplication.repository;

import com.hackathon.ChurnAplication.model.ModelInput;
import org.springframework.data.jpa.repository.JpaRepository;

// La interface extiende de JpaRepository y los parámetros son <Entidad a gestionar, Tipo de dato del ID de la Entidad>
public interface ModelInputRepository extends JpaRepository<ModelInput, Long> {
}
