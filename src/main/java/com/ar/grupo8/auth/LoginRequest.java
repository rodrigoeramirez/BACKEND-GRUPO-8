package com.ar.grupo8.auth;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//se utiliza para representar la solicitud de inicio de sesión (login) en tu aplicación. Esta clase es una representación del cuerpo de la solicitud HTTP que contiene los datos que el usuario envía al servidor para autenticarse (normalmente el nombre de usuario y la contraseña).

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotNull(message = "El nombre de usuario es obligatorio")
    private String username;

    @NotNull(message = "La clave es obligatoria")
    private String clave;
}
