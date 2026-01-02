package com.hackathon.ChurnAplication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerSummaryDTO {
    private String customerId;
    private String externalId;
    private String name;
}
