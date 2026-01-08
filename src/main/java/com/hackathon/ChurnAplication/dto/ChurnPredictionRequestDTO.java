package com.hackathon.ChurnAplication.dto;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChurnPredictionRequestDTO {
 /* Función:
        - Este DTO representa EXACTAMENTE el JSON que espera tu API de FastAPI (Python).
        - Solo contiene los datos necesarios para predecir, sin IDs de base de datos.
     */

    @NotNull(message = "El tiempo de contrato es obligatorio")
    @Min(value = 0, message = "El tiempo no puede ser negativo")
    @JsonProperty("customer_tenure")
    @JsonAlias("tiempo_contrato_meses")
    private Integer customerTenure;

    @NotNull(message = "El monto mensual es obligatorio")
    @Min(value = 0, message = "El monto mensual no puede ser negativo")
    @JsonProperty("account_charges_monthly")
    @JsonAlias("accountChargesMonthly")
    private Double accountChargesMonthly;

    @NotNull(message = "El dato del cliente (es un cliente nuevo) es obligatorio")
    @JsonProperty("cliente_nuevo")
    @JsonAlias("es_nuevo_cliente")
    private Boolean isNewCustomer;

    @NotNull(message = "El dato del cliente (es un contrato mensual) es obligatorio")
    @JsonProperty("contrato_mensual")
    @JsonAlias("es_contrato_mensual")
    private Boolean isMonthlyContract;

    @NotNull(message = "El dato del cliente (tiene un alto costo) es obligatorio")
    @JsonProperty("alto_costo")
    @JsonAlias("tiene_alto_costo")
    private Boolean isHighCost;
}

