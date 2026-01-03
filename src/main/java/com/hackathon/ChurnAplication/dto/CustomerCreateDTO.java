package com.hackathon.ChurnAplication.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class CustomerCreateDTO {
    /* Función de la clase:
       - Define qué datos mínimos se necesitan para registrar un cliente
    **/


    @NotBlank(message = "El nombre del cliente es obligatorio") // Validación. Asegura que el nombre no venga vacío ni sea solo espacios en blanco.
    private String name;

    @NotBlank(message = "El ID externo (identificador de negocio) es obligatorio")// Este es el ID que usa el negocio (ej: "CLIENTE-ABC-123").
    private String externalId;

    // Nota: No pedimos 'createdAt' ni 'id' porque esos los generamos nosotros automáticamente en el sistema.
}
