package com.hackathon.ChurnAplication.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchPredictionResponseDTO {
    /* Función:
       - Entregar un reporte detallado tras procesar un archivo CSV masivo.
    */
    
    private int totalRows;       // Total de filas leídas del archivo
    private int successCount;    // Total de predicciones exitosas
    private int failureCount;    // Total de fallos
    private List<String> errors; // Lista de mensajes de error
    
    // Lista de predicciones exitosas para mostrar en la tabla
    private List<CustomerPredictionDTO> successfulPredictions;
}
