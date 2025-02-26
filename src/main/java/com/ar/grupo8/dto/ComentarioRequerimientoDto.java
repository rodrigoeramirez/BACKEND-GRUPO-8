package com.ar.grupo8.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ComentarioRequerimientoDto {
    @NotNull(message = "El asunto no puede estar vacío")
    private String asunto; // Asunto del comentario
    @NotNull(message = "La descripcion no puede estar vacía")
    private String descripcion;
    @NotNull(message = "El requerimientoCodigo no puede estar vacío")
    private String requerimientoCodigo;
    @NotNull(message = "El emisorId no puede estar vacío")
    private Integer usuarioEmisorId;
    private String username;
    @NotNull(message = "La fecha y hora no puede estar vacía")
    private LocalDateTime fechaHora;
    private List<ArchivoAdjuntoDto> archivosAdjuntos; // Archivos adjuntos al comentario
}
