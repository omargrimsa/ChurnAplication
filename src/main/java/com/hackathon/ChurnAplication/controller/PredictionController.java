package com.hackathon.ChurnAplication.controller;


import com.hackathon.ChurnAplication.dto.BatchPredictionResponseDTO;
import com.hackathon.ChurnAplication.dto.CustomerPredictionDTO;
import com.hackathon.ChurnAplication.dto.ModelInputDTO;
import com.hackathon.ChurnAplication.dto.PredictionResultDTO;
import com.hackathon.ChurnAplication.service.CsvPredictionService;
import com.hackathon.ChurnAplication.service.PredictionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/predictions")
@RequiredArgsConstructor
public class PredictionController {

    private final PredictionService predictionService;
    private final CsvPredictionService csvPredictionService;

    /* Endpoint: POST /api/predictions
       Función:
       - Recibe el JSON con los datos del cliente.
       - @Valid: Ejecuta las validaciones definidas en ModelInputDTO.
         Si falla, Spring lanza una excepción (MethodArgumentNotValidException) antes de entrar al método.
       - Llama al servicio para procesar la predicción.
    */
    @PostMapping
    public ResponseEntity<PredictionResultDTO> predictChurn(@Valid @RequestBody ModelInputDTO inputDTO) {

        PredictionResultDTO result = predictionService.processAndSavePrediction(inputDTO);

        return ResponseEntity.ok(result);
    }

    /* Endpoint: GET /api/predictions/customer/{customerId}
       Función:
       - Obtener el historial completo de predicciones de un cliente específico.
    */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<CustomerPredictionDTO>> getCustomerPredictions(@PathVariable Long customerId) {
        List<CustomerPredictionDTO> predictions = predictionService.getPredictionsByCustomer(customerId);
        return ResponseEntity.ok(predictions);
    }

    /* Endpoint: POST /api/predictions/upload-csv
       Función:
       - Carga masiva de predicciones desde un archivo CSV.
       - Retorna un reporte con el resultado del procesamiento.
    */
    @PostMapping(value = "/upload-csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BatchPredictionResponseDTO> uploadCsv(@RequestParam("file") MultipartFile file) {
        BatchPredictionResponseDTO report = csvPredictionService.processCsvFile(file);
        return ResponseEntity.ok(report);
    }

}
