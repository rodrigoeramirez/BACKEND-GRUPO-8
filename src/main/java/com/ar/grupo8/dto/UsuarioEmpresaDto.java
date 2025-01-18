package com.ar.grupo8.dto;

import com.ar.grupo8.models.Cargo;
import com.ar.grupo8.models.Departamento;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


// JsonInclude es una anotación de la biblioteca Jackson, que se utiliza en Java para personalizar cómo los objetos se convierten a formato JSON (serialización) y cómo los datos JSON se convierten a objetos Java (deserialización).
@JsonInclude(JsonInclude.Include.NON_NULL) // Esto hace que los atributos con valores null no sean incluidos en la salida JSON. Por ejemplo: cuando retornaba los usuariosEmpresa me mostraba "clave=null" porque desde el metodo (en el servicio) no lo enviaba, pero si se encuentra como atributo en el DTO.
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor// Crea los getters y setters automaticamente.
public class UsuarioEmpresaDto {

    // El legajo es autoincremental, es la clave principal del usuario empresa
    @Min(1) // Mínimo valor permitido
    private Integer legajo;

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

    @NotNull(message = "El ID del departamento no puede ser nulo")
    private Long departamento_id;

    @NotNull(message = "El ID del cargo no puede ser nulo")
    private Long cargo_id;

    private String departamento;

    private String cargo;

    public UsuarioEmpresaDto(String nombre, String apellido, String username, String email, Integer legajo, Cargo cargo, Departamento departamento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.username = username;
        this.email = email;
        this.legajo = legajo;
        this.cargo = cargo != null ? cargo.getNombre() : null;  // O la propiedad que necesites
        this.departamento = departamento != null ? departamento.getNombre() : null;  // O la propiedad que necesites
        this.cargo_id = cargo != null ? cargo.getId() : null;  // Asegúrate de inicializar el id
        this.departamento_id = departamento != null ? departamento.getId() : null;  // Asegúrate de inicializar el id
    }
}
