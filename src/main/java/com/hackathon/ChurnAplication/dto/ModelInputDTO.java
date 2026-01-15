package com.hackathon.ChurnAplication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
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
    @JsonProperty("Meses_contrato")
    private Float customerTenure;

    @NotNull(message = "El total es obligatorio")
    @Min(value = 0, message = "El total no puede ser negativo")
    @JsonProperty("Total")
    private Float accountTotal;

    @NotNull(message = "El cargo mensual es obligatorio")
    @Min(value = 0, message = "El cargo mensual no puede ser negativo")
    @JsonProperty("Factura_mensual")
    private Float accountChargesMonthly; // Cargo mensual de factura (Factura_mensual)

    @NotNull(message = "Debe especificar si es contrato es mensual")
    @JsonProperty("Contrato_mensual")
    private Boolean isMonthlyContract;

    @NotNull(message = "Debe especificar si el cliente tiene un medio de pago electronico")
    @JsonProperty("Pago_chequera_electronica")
    private Boolean isElectronicPay;

    @NotNull(message = "Debe especificar si el cliente tiene soporte")
    @JsonProperty("Soporte_tecnico")
    private Boolean hasTechnicalSupport;

    @NotNull(message = "Debe especificar si el cliente tiene seguridad online")
    @JsonProperty("Seguridad_online")
    private Boolean hasOnlineSecurity;

    @NotNull(message = "Debe especificar si el cliente tiene un contrato por dos años")
    @JsonProperty("Contrato_2_años")
    private Boolean hasTwoYearContract;

    @NotNull(message = "Debe especificar si el cliente tiene facturacion en linea")
    @JsonProperty("Factura_online")
    private Boolean hasOnlineInvoices;

    @NotNull(message = "Debe especificar si el cliente tiene un contrato por un año")
    @JsonProperty("Contrato_1_año")
    private Boolean hasOneYearContract;




    private LocalDateTime sentedAt;

    // Datos del Customer
    @NotNull(message = "El ID del cliente es obligatorio")
    @JsonProperty("customerId")
    private Long customerId; // Necesario para vincular el registro al cliente

    // Datos del PredictionResult
    //private Long predictionResultId;

}
