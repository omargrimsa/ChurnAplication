package com.hackathon.ChurnAplication.model;


import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "prediction_results")
public class PredictionResult {
    /* Función de la clase:
      - Persistir la respuesta que nos devuelve la API de Inteligencia Artificial.
      - Vincularse con el input original para saber qué datos generaron esta predicción.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador del resultado

    @OneToOne(optional = false) // Relación 1 a 1 con el input enviado a FastAPI, Cada PredictionResult corresponde a un input enviado
    @JoinColumn(name = "model_input_id", nullable = false, unique = true) // FK → model_inputs.id (la clase PredictionResult usa la FK por eso usa @JoinColumn y no mappedBy)
    private ModelInput modelInput; // Input que dio origen a este PredictionResult

    @Column(name = "churn_probability", nullable = false) // Probabilidad de churn
    private Double churnProbability; // Probabilidad de churn

    @Column(name = "will_cancel", nullable = false)
    private Boolean willCancel; // Interpretación final que indica indica se habrá Churn o no

    @Column(name = "predicted_at", nullable = false)
    private LocalDateTime predictedAt; // Fecha/hora de la predicción



}
