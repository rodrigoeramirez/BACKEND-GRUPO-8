package com.ar.grupo8.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioEmpresaDto {

    @NotNull(message = "El nombre no puede ser nulo")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombre;

    @NotNull(message = "El apellido no puede ser nulo")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    private String apellido;

    @NotNull(message = "El username no puede ser nulo")
    @Size(min = 4, max = 20, message = "El username debe tener entre 4 y 20 caracteres")
    private String username;

    @NotNull(message = "La clave no puede ser nula")
    @Size(min = 8, message = "La clave debe tener al menos 8 caracteres")
    private String clave;

    @NotNull(message = "El email no puede ser nulo")
    @Email(message = "Debe proporcionar un email válido")
    private String email;

    @NotNull(message = "El legajo no puede ser nulo")
    @Size(min = 5, max = 15, message = "El legajo debe tener entre 5 y 15 caracteres")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "El legajo solo puede contener letras y números")
    private String legajo;

    @NotNull(message = "El ID del departamento no puede ser nulo")
    private Long departamento_id;

    @NotNull(message = "El ID del cargo no puede ser nulo")
    private Long cargo_id;

}
