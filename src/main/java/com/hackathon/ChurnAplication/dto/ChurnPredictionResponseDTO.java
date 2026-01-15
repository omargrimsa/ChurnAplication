package com.hackathon.ChurnAplication.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChurnPredictionResponseDTO {
     /* Función:
        - Mapear la respuesta JSON que viene del servidor FastAPI.
        - Ejemplo real: { "prevision": "Va a cancelar", "probabilidad_de_churn": 1 }
     */

    @JsonProperty("probabilidad")
    private Float churnProbability; // Probabilidad de churn

    @JsonProperty("mensaje")
    private String prevision; // Recibe el texto: "Va a cancelar"

    // Método auxiliar: Convierte el texto de la API al Boolean que usa tu sistema
    public Boolean getWillCancel() {
        return "Va a cancelar".equalsIgnoreCase(this.prevision);
    }
}
