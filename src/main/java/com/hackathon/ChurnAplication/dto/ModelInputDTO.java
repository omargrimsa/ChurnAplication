package com.hackathon.ChurnAplication.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ModelInputDTO {
    /* Función:
        - Recibir los datos crudos desde el cliente (Postman/Frontend).
        - Validar la integridad de los datos antes de procesar nada.
     */

    // Datos del ModelInput
   // private Long id;

    @NotNull(message = "El tiempo de contrato (tenure) es obligatorio")
    @Min(value = 0, message = "El tiempo de contrato no puede ser negativo")
    private Integer customerTenure;

    @NotNull(message = "El cargo mensual es obligatorio")
    @Min(value = 0, message = "El cargo mensual no puede ser negativo")
    private Double accountchargesMonthly;


    @NotNull(message = "Debe especificar si es un nuevo cliente")
    private Boolean isNewCustomer;

    @NotNull(message = "Debe especificar si tiene contrato mensual")
    private Boolean isMonthlyContract;

    @NotNull(message = "Debe especificar si es de alto costo")
    private Boolean isHighCost;

    private LocalDateTime sentAt;

    // Datos del Customer
    @NotNull(message = "El ID del cliente es obligatorio")
    private Long customerId; // Necesario para vincular el registro al cliente

    // Datos del PredictionResult
    //private Long predictionResultId;

}
