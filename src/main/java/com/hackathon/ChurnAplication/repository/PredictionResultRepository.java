package com.hackathon.ChurnAplication.repository;

import com.hackathon.ChurnAplication.model.PredictionResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PredictionResultRepository extends JpaRepository<PredictionResult, Long> {
}
