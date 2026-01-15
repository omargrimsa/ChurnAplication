package com.hackathon.ChurnAplication.service;


import com.hackathon.ChurnAplication.dto.ChurnPredictionRequestDTO;
import com.hackathon.ChurnAplication.dto.ChurnPredictionResponseDTO;
import com.hackathon.ChurnAplication.dto.ModelInputDTO;
import com.hackathon.ChurnAplication.dto.PredictionResultDTO;
import com.hackathon.ChurnAplication.model.Customer;
import com.hackathon.ChurnAplication.model.ModelInput;
import com.hackathon.ChurnAplication.model.PredictionResult;
import com.hackathon.ChurnAplication.repository.CustomerRepository;
import com.hackathon.ChurnAplication.repository.ModelInputRepository;
import com.hackathon.ChurnAplication.repository.PredictionResultRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor // Inyección de dependencias automática para campos final
public class PredictionService {
    /* Funcion de la clase:
       - Llamar primero a la API de prediccion, guardar datos después si hay respuesta satisfactoria.
    * */

    private final ModelInputRepository modelInputRepository;
    private final PredictionResultRepository predictionResultRepository;
    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate; // RestTemplate es una clase central en Spring Framework diseñada para realizar llamadas HTTP síncronas.

    @Value("${external.api.url}") // URL de la API de Python
    private String externalApiUrl;
    // external.api.url: Es el nombre de la clave (key) que Spring buscará en el archivo  application.properties

    @Transactional // Asegura atomicidad: o se guarda todo (input + resultado) o nada.
    public PredictionResultDTO processAndSavePrediction(ModelInputDTO inputDTO) {

        // 1. Validar existencia del cliente
        Customer customer = customerRepository.findById(inputDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + inputDTO.getCustomerId()));

        // 2. Preparar el Request para la API Externa (Mapeo de DTO interno a DTO externo)
        ChurnPredictionRequestDTO requestToApi = ChurnPredictionRequestDTO.builder()
                .customerTenure(inputDTO.getCustomerTenure())
                .accountTotal(inputDTO.getAccountTotal())
                .accountChargesMonthly(Double.valueOf(inputDTO.getAccountChargesMonthly()))
                .isMonthlyContract(inputDTO.getIsMonthlyContract())
                .isElectronicPay(inputDTO.getIsElectronicPay())
                .hasTechnicalSupport(inputDTO.getHasTechnicalSupport())
                .hasOnlineSecurity(inputDTO.getHasOnlineSecurity())
                .hasTwoYearContract(inputDTO.getHasTwoYearContract())
                .hasOnlineInvoices(inputDTO.getHasOnlineInvoices())
                .hasOneYearContract(inputDTO.getHasOneYearContract())
                .build();

        // 3. LLAMADA A LA API EXTERNA
        // NOTA: Hacemos esto ANTES de guardar en BD. Si falla, se lanza excepción y no se guarda nada.
        ChurnPredictionResponseDTO apiResponse;
        try {
            // DEBUG: Imprimir respuesta cruda para ver los nombres de los campos
            String jsonRaw = restTemplate.postForObject(externalApiUrl, requestToApi, String.class);
            System.out.println("RESPUESTA API PYTHON: " + jsonRaw);
            
            // Convertir manualmente (necesitas importar ObjectMapper)
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            apiResponse = mapper.readValue(jsonRaw, ChurnPredictionResponseDTO.class);
            
        } catch (Exception e) {
            // Si la API no responde o da error, notificamos al usuario y cortamos el flujo.
            throw new RuntimeException("Error al conectar con el servicio de predicción externo: " + e.getMessage());
        }

        if (apiResponse == null || apiResponse.getChurnProbability() == null) {
            throw new RuntimeException("El servicio de predicción devolvió una respuesta incompleta o nula.");
        }

        // --- Si llegamos aquí, hubo éxito en la predicción. Procedemos a persistir. ---

        // 4. Guardar el Input (Evidencia de lo enviado)
        ModelInput modelInput = new ModelInput();
        modelInput.setCustomer(customer);
        modelInput.setCustomerTenure(inputDTO.getCustomerTenure());
        modelInput.setAccountTotal(inputDTO.getAccountTotal());
        modelInput.setAccountChargesMonthly(inputDTO.getAccountChargesMonthly());
        modelInput.setIsMonthlyContract(inputDTO.getIsMonthlyContract());
        modelInput.setIsElectronicPay(inputDTO.getIsElectronicPay());
        modelInput.setHasTechnicalSupport(inputDTO.getHasTechnicalSupport());
        modelInput.setHasOnlineSecurity(inputDTO.getHasOnlineSecurity());
        modelInput.setHasTwoYearContract(inputDTO.getHasTwoYearContract());
        modelInput.setHasOnlineInvoices(inputDTO.getHasOnlineInvoices());
        modelInput.setHasOneYearContract(inputDTO.getHasOneYearContract());
        modelInput.setSentedAt(LocalDateTime.now());

        ModelInput savedInput = modelInputRepository.save(modelInput);

        // 5. Guardar el Resultado (La respuesta de la API)
        PredictionResult result = new PredictionResult();
        result.setModelInput(savedInput); // Relación 1 a 1
        result.setChurnProbability(apiResponse.getChurnProbability());
        result.setWillCancel(apiResponse.getWillCancel());
        result.setPredictedAt(LocalDateTime.now());

        PredictionResult savedResult = predictionResultRepository.save(result);

        // 6. Retornar DTO final
        return new PredictionResultDTO(
                savedResult.getId(),
                savedResult.getChurnProbability(),
                savedResult.getWillCancel(),
                savedResult.getPredictedAt()
        );

    }
}



        
