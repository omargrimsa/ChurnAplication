package com.hackathon.ChurnAplication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDetailDTO {
    private String externalId;
    private String name;
    private LocalDateTime createdAt;

    // Lita de inputs de predicion del cliente
    private List<ModelInputDTO> history;
}
