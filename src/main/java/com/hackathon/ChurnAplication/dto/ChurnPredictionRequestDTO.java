package com.hackathon.ChurnAplication.dto;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChurnPredictionRequestDTO {
/* Funciones de la clase:
   - Enviar solo los "inputs" que el modelo de IA espera recibir.
   - No envia IDs (ni internos ni externos) porque el modelo no los usa para calcular.
 */

    @NotNull(message = "El tiempo de contrato es obligatorio")
    @Min(value = 0, message = "El tiempo no puede ser negativo")
    @JsonProperty("customerTenure")
    @JsonAlias("tiempo_contrato_meses")
    private Integer customerTenure;

    @NotNull(message = "El monto mensual es obligatorio")
    @Min(value = 0, message = "El monto mensual no puede ser negativo")
    @JsonProperty("uso_mensual")
    @JsonAlias("accountChargesMonthly")
    private Double accountChargesMonthly;

    @NotNull(message = "El dato del cliente (es un cliente nuevo) es obligatorio")
    @JsonProperty("isNewCustomer")
    @JsonAlias("es_nuevo_cliente")
    private Boolean isNewCustomer;

    @NotNull(message = "El dato del cliente (es un contrato mensual) es obligatorio")
    @JsonProperty("isMonthlyContract")
    @JsonAlias("es_contrato_mensual")
    private Boolean isMonthlyContract;

    @NotNull(message = "El dato del cliente (tiene un alto costo) es obligatorio")
    @JsonProperty("isHighCost")
    @JsonAlias("tiene_alto_costo")
    private Boolean isHighCost;
}

