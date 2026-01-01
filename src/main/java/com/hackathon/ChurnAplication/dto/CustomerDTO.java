package com.hackathon.ChurnAplication.dto;

import com.hackathon.ChurnAplication.model.ModelInput;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private Long id;
    private String externalId;
    private String name;
    private LocalDateTime createdAt;

    // Datos del ModelInput
    private List<ModelInputDTO> modelInputs; // Historial de inputs asociados al cliente (cliente puede tener muchos inputs)

}
