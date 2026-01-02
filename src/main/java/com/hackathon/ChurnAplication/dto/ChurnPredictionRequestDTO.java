package com.hackathon.ChurnAplication.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChurnPredictionRequestDTO {
/* Funciones de la clase:
   - Enviar solo los "inputs" que el modelo de IA espera recibir.
   - No envia IDs (ni internos ni externos) porque el modelo no los usa para calcular.
 */

    private Integer customerTenure;
    private Double accountChargesMonthly;
    private Boolean isNewCustomer;
    private Boolean isMonthlyContract;
    private Boolean isHighCost;
}

