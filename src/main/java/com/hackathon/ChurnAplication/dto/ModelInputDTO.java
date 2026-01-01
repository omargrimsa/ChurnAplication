package com.hackathon.ChurnAplication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModelInputDTO {
    // Datos del ModelInput
    private Long id;
    private Integer customerTenure;
    private Double accountchargesMonthly;
    private Boolean isNewCustomer;
    private Boolean isMonthlyContract;
    private Boolean isHighCost;
    private LocalDateTime sentAt;

    // Datos del Customer
    private Long customerId;

    // Datos del PredictionResult
    private Long predictionResultId;

}
