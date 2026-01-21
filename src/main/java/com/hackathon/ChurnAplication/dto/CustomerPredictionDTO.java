package com.hackathon.ChurnAplication.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerPredictionDTO {
    /* Función:
       - Consolidar en un solo objeto los datos de entrada (ModelInput) y salida (PredictionResult)
       - Servir como historial completo de una predicción para mostrar en el frontend.
    */

    // --- Datos del Cliente (Nuevo) ---
    private String externalId;
    private String customerName;

    // --- Datos del Input (Lo que se envió) ---
    private Long modelInputId;
    private Float customerTenure;
    private Float accountTotal;
    private Float accountChargesMonthly;
    
    // Booleanos de entrada
    private Boolean isMonthlyContract;
    private Boolean isElectronicPay;
    private Boolean hasTechnicalSupport;
    private Boolean hasOnlineSecurity;
    private Boolean hasTwoYearContract;
    private Boolean hasOnlineInvoices;
    private Boolean hasOneYearContract;
    
    private LocalDateTime sentAt;

    // --- Datos del Resultado (Lo que respondió la IA) ---
    private Long predictionResultId;
    private Float churnProbability;
    private Boolean willCancel;
    private LocalDateTime predictedAt;
}
