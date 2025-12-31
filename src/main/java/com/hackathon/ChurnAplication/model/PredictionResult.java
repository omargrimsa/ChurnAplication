package com.hackathon.ChurnAplication.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "prediction_results")
public class PredictionResult {
    /* Función de la clase:
       - Guardar el resultado del modelo
       - Permitir Comparar modelos
       - Guardar versiones
       - Auditoría de decisiones */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador del resultado

    @OneToOne(optional = false) // Relación 1 a 1 con el input, Cada resultado corresponde a un input
    @JoinColumn(name = "model_input_id", nullable = false, unique = true) // FK → model_inputs.id
    private ModelInput modelInput; // Input que generó este resultado

    @Column(name = "churn_probability", nullable = false) // Probabilidad de churn
    private Double churnProbability; // Probabilidad de churn

    @Column(name = "will_cancel", nullable = false)
    private Boolean willCancel; // Interpretación final

    @Column(name = "predicted_at", nullable = false)
    private LocalDateTime predictedAt; // Fecha/hora de la predicción



}
