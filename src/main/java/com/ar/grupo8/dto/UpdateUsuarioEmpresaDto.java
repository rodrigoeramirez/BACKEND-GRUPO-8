package com.ar.grupo8.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data // Crea los getters y setters automaticamente.
@JsonInclude(JsonInclude.Include.NON_NULL) // indica que solo se incluirán los campos que no sean nulos. Esto es especialmente útil para evitar enviar datos innecesarios, como campos vacíos, al cliente o a una API.
public class UpdateUsuarioEmpresaDto {

    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombre;

    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    private String apellido;

    @Size(min = 4, max = 20, message = "El username debe tener entre 4 y 20 caracteres")
    private String username;

    @Size(min = 8, message = "La clave debe tener al menos 8 caracteres")
    private String clave;

    @Email(message = "Debe proporcionar un email válido")
    private String email;

    private Long departamento_id;

    private Long cargo_id;

    // Getters y Setters
}