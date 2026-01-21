package com.hackathon.ChurnAplication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

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
    @CsvBindByName(column = "Meses_contrato", required = true)
    private Float customerTenure;

    @NotNull(message = "El total es obligatorio")
    @Min(value = 0, message = "El total no puede ser negativo")
    @JsonProperty("Total") // Para JSON
    @CsvBindByName(column = "Total", required = true) // Para CSV
    private Float accountTotal;

    @NotNull(message = "El cargo mensual es obligatorio")
    @Min(value = 0, message = "El cargo mensual no puede ser negativo")
    @JsonProperty("Factura_mensual") // Para JSON
    @CsvBindByName(column = "Factura_mensual", required = true) // Para CSV
    private Float accountChargesMonthly; // Cargo mensual de factura (Factura_mensual)

    @NotNull(message = "Debe especificar si es contrato es mensual")
    @JsonProperty("Contrato_mensual") // Para JSON
    @CsvBindByName(column = "Contrato_mensual", required = true) // Para CSV
    private Boolean isMonthlyContract;

    @NotNull(message = "Debe especificar si el cliente tiene un medio de pago electronico")
    @JsonProperty("Pago_chequera_electronica") // Para JSON
    @CsvBindByName(column = "Pago_chequera_electronica", required = true) // Para CSV
    private Boolean isElectronicPay;

    @NotNull(message = "Debe especificar si el cliente tiene soporte")
    @JsonProperty("Soporte_tecnico") // Para JSON
    @CsvBindByName(column = "Soporte_tecnico", required = true) // Para CSV
    private Boolean hasTechnicalSupport;

    @NotNull(message = "Debe especificar si el cliente tiene seguridad online")
    @JsonProperty("Seguridad_online") // Para JSON
    @CsvBindByName(column = "Seguridad_online", required = true) // Para CSV
    private Boolean hasOnlineSecurity;

    @NotNull(message = "Debe especificar si el cliente tiene un contrato por dos años")
    @JsonProperty("Contrato_2_años") // Para JSON
    @CsvBindByName(column = "Contrato_2_años", required = true) // Para CSV
    private Boolean hasTwoYearContract;

    @NotNull(message = "Debe especificar si el cliente tiene facturacion en linea")
    @JsonProperty("Factura_online") // Para JSON
    @CsvBindByName(column = "Factura_online", required = true) // Para CSV
    private Boolean hasOnlineInvoices;

    @NotNull(message = "Debe especificar si el cliente tiene un contrato por un año")
    @JsonProperty("Contrato_1_año") // Para JSON
    @CsvBindByName(column = "Contrato_1_año", required = true) // Para CSV
    private Boolean hasOneYearContract;




    // Este campo no parece venir del CSV, por lo que no necesita la anotación
    private LocalDateTime sentedAt; 

    // Datos del Customer
    @NotNull(message = "El ID del cliente es obligatorio")
    @JsonProperty("customerId") // Para JSON
    @CsvBindByName(column = "Id_cliente", required = true) // Para CSV
    private Long customerId; // Necesario para vincular el registro al cliente

    // Datos del PredictionResult
    //private Long predictionResultId;

}
