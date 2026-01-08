package com.hackathon.ChurnAplication.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChurnPredictionResponseDTO {
     /* Función:
        - Mapear la respuesta JSON que viene del servidor FastAPI.
        - Ejemplo real: { "prevision": "Va a cancelar", "probabilidad_de_churn": 1 }
     */

    @JsonProperty("probabilidad_de_churn")
    private Double churnProbability; // Probabilidad de churn

    @JsonProperty("prevision")
    private String prevision; // Recibe el texto: "Va a cancelar"

    // Método auxiliar: Convierte el texto de la API al Boolean que usa tu sistema
    public Boolean getWillCancel() {
        return "Va a cancelar".equalsIgnoreCase(this.prevision);
    }
}
