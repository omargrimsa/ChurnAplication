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
        - Ejemplo esperado: { "renuncia": true, "probabilidad": 0.85 }
     */

    @JsonProperty("probabilidad")
    private Float churnProbability; // Probabilidad de churn

    @JsonProperty("renuncia")
    private Boolean renuncia; // Nuevo campo booleano directo desde la API

    // Método auxiliar: Adapta el campo "renuncia" al estándar "willCancel" de tu sistema interno
    public Boolean getWillCancel() {
        // Si renuncia es null (por si acaso), devolvemos false o manejamos el error
        return Boolean.TRUE.equals(this.renuncia);
    }
}
