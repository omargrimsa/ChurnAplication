package com.hackathon.ChurnAplication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Prediction {

    private Long id;  // Identificador único de la predicción

    private String clientId;  // Identificador del cliente (email o código)
    private Integer age;  // Edad del cliente
    private Double balance;  // Saldo de la cuenta
    private Integer tenure;  // Tiempo como cliente (meses o años)
    private Integer numProducts;  // Número de productos que tiene
    private Boolean hasCrCard;  // Indica si posee tarjeta de crédito
    private Boolean isActiveMember;  // Indica si está activo actualmente
    private Double estimatedSalary;  // Salario estimado
    private Double churnProbability;  // Probabilidad de abandono calculada
    private LocalDateTime predictedAt;  // Fecha y hora de la predicción


}
