package com.hackathon.ChurnAplication.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "model_inputs")
public class ModelInput {
    /* Función de la clase:
     - Guardar exactamente lo que vio el modelo de predccion + id cliente al que pertenecen los datos y el resultado de la prediccion
     - Permitir Envíos masivos
     - Comparación entre inputs y outputs
     - Trazabilidad */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID técnico autoincrementable
    private Long id; // Identificador del input

    @ManyToOne(optional = false) // Muchos registros de esta entidad pertenecen a UN solo Customer, la relación es obligatoria
    @JoinColumn(name = "customer_id", nullable = false) // customer_id es FK hacia customers.id (la clase ModelInput usa la FK por eso usa @JoinColumn y no mappedBy)
    private Customer customer; // Cliente al que pertenecen los datos

    // DATOS QUE SE ENVIAN AL MODELO
    @Column(name = "customer_tenure", nullable = false)
    private Float customerTenure; // Tiempo como cliente en meses (Meses_contrato)

    @Column(name = "total_charges", nullable = false)
    private Float accountTotal; // Total de cargos al dia en dinero (Total)

    @Column(name = "monthly_charges", nullable = false)
    private Float accountChargesMonthly; // Cargo mensual de factura (Factura_mensual)

    @Column(name = "is_monthly_contract", nullable = false)
    private Boolean isMonthlyContract; // Indica si el contrato es mensual (Contrato_mensual)

    @Column(name = "is_electronic_pay", nullable = false)
    private Boolean isElectronicPay; // Se paga mediante pedio electrónico (Pago_chequera_electronica)

    @Column(name = "has_technical_support", nullable = false)
    private Boolean hasTechnicalSupport; // Indica si es cliente conratdo soporte tecnico (Soporte_tecnico)

    @Column(name = "has_online_security", nullable = false)
    private Boolean hasOnlineSecurity; // Indica si es cliente tiene contratada seguridad en linea (Seguridad_online)

    @Column(name = "has_two_year_contract", nullable = false)
    private Boolean hasTwoYearContract; // Indica si es cliente tiene un contrato de dos años (Contrato_2_años)

    @Column(name = "has_online_Invoices", nullable = false)
    private Boolean hasOnlineInvoices; // Indica si es cliente tiene facturacion en linea (Factura_online)

    @Column(name = "has_one_year_contract", nullable = false)
    private Boolean hasOneYearContract; // Indica si es cliente tiene un contrato de un año (Contrato_1_año)

    @Column(name = "sent_at", nullable = false)
    private LocalDateTime sentedAt; // Fecha/hora en que se envió al modelo

    @OneToOne(mappedBy = "modelInput", cascade = CascadeType.ALL) // Relación 1–1 con PredictionResult
    private PredictionResult predictionResult;

}
