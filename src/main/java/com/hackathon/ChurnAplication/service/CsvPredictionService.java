package com.hackathon.ChurnAplication.service;

import com.hackathon.ChurnAplication.dto.BatchPredictionResponseDTO;
import com.hackathon.ChurnAplication.dto.CustomerPredictionDTO;
import com.hackathon.ChurnAplication.dto.ModelInputDTO;
import com.hackathon.ChurnAplication.dto.PredictionResultDTO;
import com.hackathon.ChurnAplication.model.Customer;
import com.hackathon.ChurnAplication.repository.CustomerRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CsvPredictionService {

    private final PredictionService predictionService;
    private final CustomerRepository customerRepository;

    public BatchPredictionResponseDTO processCsvFile(MultipartFile file) {
        List<String> errors = new ArrayList<>();
        List<CustomerPredictionDTO> successfulPredictions = new ArrayList<>();
        int successCount = 0;
        int failureCount = 0;
        int totalRows = 0;

        if (file.isEmpty()) {
            throw new RuntimeException("El archivo CSV está vacío.");
        }

        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CSVReader csvReader = new CSVReader(reader);
            
            String[] header = csvReader.readNext(); // Leer encabezados
            if (header == null) {
                throw new RuntimeException("El archivo CSV no tiene contenido válido.");
            }

            String[] line;
            int lineNumber = 1; 

            while ((line = csvReader.readNext()) != null) {
                lineNumber++;
                totalRows++;

                try {
                    if (line.length < 11) {
                        throw new IllegalArgumentException("Fila incompleta (faltan columnas)");
                    }

                    // 1. Obtener ID Externo
                    String externalId = line[0].trim();
                    
                    // 2. Buscar cliente
                    Optional<Customer> customerOpt = customerRepository.findByExternalId(externalId);
                    
                    if (customerOpt.isEmpty()) {
                        throw new IllegalArgumentException("Cliente no encontrado con ID Externo: " + externalId);
                    }
                    
                    Customer customer = customerOpt.get();

                    // 3. Construir DTO de entrada
                    ModelInputDTO inputDTO = new ModelInputDTO();
                    inputDTO.setCustomerId(customer.getId()); 
                    inputDTO.setCustomerTenure(Float.parseFloat(line[1]));
                    inputDTO.setAccountTotal(Float.parseFloat(line[2]));
                    inputDTO.setAccountChargesMonthly(Float.parseFloat(line[3]));
                    inputDTO.setIsMonthlyContract(parseBoolean(line[4]));
                    inputDTO.setIsElectronicPay(parseBoolean(line[5]));
                    inputDTO.setHasTechnicalSupport(parseBoolean(line[6]));
                    inputDTO.setHasOnlineSecurity(parseBoolean(line[7]));
                    inputDTO.setHasTwoYearContract(parseBoolean(line[8]));
                    inputDTO.setHasOnlineInvoices(parseBoolean(line[9]));
                    inputDTO.setHasOneYearContract(parseBoolean(line[10]));

                    // 4. Procesar predicción
                    PredictionResultDTO resultDTO = predictionService.processAndSavePrediction(inputDTO);
                    
                    // 5. Crear DTO para el reporte
                    CustomerPredictionDTO reportDTO = CustomerPredictionDTO.builder()
                            .externalId(customer.getExternalId())
                            .customerName(customer.getName())
                            .customerTenure(inputDTO.getCustomerTenure())
                            .accountTotal(inputDTO.getAccountTotal())
                            .accountChargesMonthly(inputDTO.getAccountChargesMonthly())
                            .isMonthlyContract(inputDTO.getIsMonthlyContract())
                            .isElectronicPay(inputDTO.getIsElectronicPay())
                            .hasTechnicalSupport(inputDTO.getHasTechnicalSupport())
                            .hasOnlineSecurity(inputDTO.getHasOnlineSecurity())
                            .hasTwoYearContract(inputDTO.getHasTwoYearContract())
                            .hasOnlineInvoices(inputDTO.getHasOnlineInvoices())
                            .hasOneYearContract(inputDTO.getHasOneYearContract())
                            .sentAt(java.time.LocalDateTime.now()) // Aproximado
                            .churnProbability(resultDTO.getChurnProbability())
                            .willCancel(resultDTO.getWillCancel())
                            .predictedAt(resultDTO.getPredictedAt())
                            .build();

                    successfulPredictions.add(reportDTO);
                    successCount++;

                } catch (Exception e) {
                    failureCount++;
                    errors.add("Fila " + lineNumber + ": " + e.getMessage());
                }
            }

        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException("Error al leer el archivo CSV: " + e.getMessage());
        }

        return BatchPredictionResponseDTO.builder()
                .totalRows(totalRows)
                .successCount(successCount)
                .failureCount(failureCount)
                .errors(errors)
                .successfulPredictions(successfulPredictions)
                .build();
    }

    private Boolean parseBoolean(String value) {
        if (value == null) return false;
        String v = value.trim().toLowerCase();
        return v.equals("true") || v.equals("1") || v.equals("si") || v.equals("yes");
    }
}
