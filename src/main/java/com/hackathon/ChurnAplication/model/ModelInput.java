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

    @Column(name = "customer_tenure", nullable = false)
    private Integer customerTenure; // Tiempo como cliente (meses)

    @Column(name = "monthly_charges", nullable = false)
    private Double accountChargesMonthly; // Cargos mensuales promedio

    @Column(name = "is_new_customer", nullable = false)
    private Boolean isNewCustomer; // Indica si es un cliente nuevo

    @Column(name = "is_monthly_contract", nullable = false)
    private Boolean isMonthlyContract; // Indica contrato mensual

    @Column(name = "is_high_cost", nullable = false)
    private Boolean isHighCost; // Indica si es cliente de alto costo

    @Column(name = "sent_at", nullable = false)
    private LocalDateTime sentAt; // Fecha/hora en que se envió al modelo

    @OneToOne(mappedBy = "modelInput", cascade = CascadeType.ALL) // Relación 1–1 con PredictionResult
    private PredictionResult predictionResult;

}
