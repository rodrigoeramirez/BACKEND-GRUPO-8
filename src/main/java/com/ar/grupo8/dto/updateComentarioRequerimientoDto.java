package com.ar.grupo8.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class updateComentarioRequerimientoDto {
    private String asunto;
    private String descripcion;
    private Long requerimientoId;
    private Integer usuarioEmisorId;
    private LocalDateTime fechaHora;
    private List<ArchivoAdjuntoDto> archivosAdjuntos; // Archivos adjuntos al comentario
}
