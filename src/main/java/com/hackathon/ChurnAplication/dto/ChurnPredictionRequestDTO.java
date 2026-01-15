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
    @JsonProperty("Meses_contrato")
    //@JsonAlias("Meses_contrato")
    private Float customerTenure;

    @NotNull(message = "El monto total es obligatorio")
    @Min(value = 0, message = "El monto total no puede ser negativo")
    @JsonProperty("Total")
   // @JsonAlias("Total")
    private Float accountTotal;

    @NotNull(message = "El monto mensual es obligatorio")
    @Min(value = 0, message = "El monto mensual no puede ser negativo")
    @JsonProperty("Factura_mensual")
    //@JsonAlias("Factura_mensual")
    private Double accountChargesMonthly;

    @NotNull(message = "El dato de si el cliente tiene un contrato mensual es obligatorio")
    @JsonProperty("Contrato_mensual")
   // @JsonAlias("Contrato_mensual")
    private Boolean isMonthlyContract;

    @NotNull(message = "El dato del si el cliente maneja un metodo de pago eletrónico es obligatorio")
    @JsonProperty("Pago_chequera_electronica")
   // @JsonAlias("Pago_chequera_electronica")
    private Boolean isElectronicPay;

    @NotNull(message = "El dato del si el cliente maneja soporte es obligatorio")
    @JsonProperty("Soporte_tecnico")
   // @JsonAlias("Soporte_tecnico")
    private Boolean hasTechnicalSupport;

    @NotNull(message = "El dato del si el cliente tiene seguriad online es obligatorio")
    @JsonProperty("Seguridad_online")
   // @JsonAlias("Seguridad_online")
    private Boolean hasOnlineSecurity;

    @NotNull(message = "El dato del si el cliente tiene un contrato por dos años es obligatorio")
    @JsonProperty("Contrato_2_años")
   // @JsonAlias("Contrato_2_años")
    private Boolean hasTwoYearContract;

    @NotNull(message = "El dato del si el cliente tiene factturacion en linea es obligatorio")
    @JsonProperty("Factura_online")
  //  @JsonAlias("Factura_online")
    private Boolean hasOnlineInvoices;

    @NotNull(message = "El dato del si el cliente tiene un contrato por un año es obligatorio")
    @JsonProperty("Contrato_1_año")
  //  @JsonAlias("Contrato_1_año")
    private Boolean hasOneYearContract;







}

