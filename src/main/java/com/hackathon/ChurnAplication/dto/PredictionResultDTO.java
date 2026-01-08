package com.hackathon.ChurnAplication.dto;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PredictionResultDTO {
    /* Función:
       - Entregar al usuario final el resultado procesado y guardado en BD.
    */

    private Long predictionId;
    private Double churnProbability;
    private Boolean willCancel;
    private LocalDateTime predictedAt;

    // Datos del ModelInput
    //private Long modelInputId;

    // Datos del Customer
    //private Long customerId;
   // private String customerName;


}
