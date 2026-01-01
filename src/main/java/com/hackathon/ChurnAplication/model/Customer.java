package com.hackathon.ChurnAplication.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {

   /* Función de la clase:
    - Representar al cliente
    - Evitar duplicar nombre/ID en cada predicción
    - Permitir histórico de predicciones por cliente */

    @Id
    @Column(name = "id", nullable = false)
    private Long id; // Identificador interno del cliente

    @Column(name = "external_id", nullable = false, unique = true)
    private String externalId; // ID externo (email, contrato, etc.)

    @Column(name = "customer_name", nullable = false)
    private String name; // Nombre del cliente

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // Fecha de registro del cliente

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)  // Relación 1 a N con ModelInput
    // cascade ALL propaga operaciones a los inputs
    private List<ModelInput> modelInputs = new ArrayList<>(); // Historial de inputs asociados al cliente (cliente puede tener muchos inputs)


}
