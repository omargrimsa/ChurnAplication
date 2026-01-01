package com.hackathon.ChurnAplication.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PredictionResultDTO {
    private Long id;
    private Double churnProbability;
    private Boolean willCancel;
    private LocalDateTime predictedAt;

    // Datos del ModelInput
    private Long modelInputId;

}
