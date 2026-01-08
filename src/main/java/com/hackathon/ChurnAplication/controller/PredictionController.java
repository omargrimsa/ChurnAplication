package com.hackathon.ChurnAplication.controller;


import com.hackathon.ChurnAplication.dto.ModelInputDTO;
import com.hackathon.ChurnAplication.dto.PredictionResultDTO;
import com.hackathon.ChurnAplication.service.PredictionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/predictions")
@RequiredArgsConstructor
public class PredictionController {

    private final PredictionService predictionService;

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



}
