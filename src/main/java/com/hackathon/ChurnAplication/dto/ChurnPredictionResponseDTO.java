package com.hackathon.ChurnAplication.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChurnPredictionResponseDTO {
    /* Funciones de la clase:
       - Recibir la predicción pura devuelta por la API externa (respuesta del modelo de IA)
    */

    private Double churnProbability;
    private Boolean willCancel;

}
