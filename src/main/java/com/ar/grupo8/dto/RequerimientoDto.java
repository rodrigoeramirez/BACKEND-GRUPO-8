package com.ar.grupo8.dto;

import com.ar.grupo8.models.Requerimiento;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RequerimientoDto {

    @NotNull(message = "El código no puede estar vacío")
    @Size(max = 20, message = "El código no puede tener más de 20 caracteres")
    private String codigo;

    @NotNull(message = "La fecha y hora de alta no puede estar vacía")
    private LocalDateTime fechaHoraAlta;

    @NotNull(message = "El asunto no puede estar vacío")
    @Size(max = 50, message = "El asunto no puede tener más de 50 caracteres")
    private String asunto;

    @NotNull(message = "La descripción no puede estar vacía")
    @Size(max = 5000, message = "La descripción no puede tener más de 5000 caracteres")
    private String descripcion;

    // Para el tipo de requerimiento
    @NotNull(message = "El tipo de requerimiento no puede estar vacío")
    private Long tipoRequerimientoId; // ID del tipo de requerimiento
    private String tipoRequerimientoDescripcion; // Descripción opcional del tipo
    private String categoriaRequerimientoDescripcion; // Un tipo requerimiento pertenece a una Categoria.
    private Long categoriaRequerimientoId;

    // Para el estado de requerimiento
    @NotNull(message = "El estado de requerimiento no puede estar vacío")
    private Long estadoRequerimientoId; // ID del estado de requerimiento
    private String estadoRequerimientoNombre; // Nombre opcional del estado


    // Para la prioridad de requerimiento
    @NotNull(message = "La prioridad de requerimiento no puede estar vacía")
    private Long prioridadRequerimientoId; // ID de la prioridad
    private String prioridadRequerimientoNombre; // Nombre opcional de la prioridad

    // Datos adicionales
    private Integer destinatarioId; // ID del destinatario (opcional)
    private String nombreCompletoDestinatario;

    @NotNull(message = "El emisor no puede estar vacío")
    private Integer emisorLegajo; // Legajo del emisor
    private String nombreCompletoEmisor;

    // Archivos adjuntos y comentarios
    private List<ArchivoAdjuntoDto> archivosAdjuntos; // DTO para archivos adjuntos
    private List<ComentarioRequerimientoDto> comentarios; // DTO para comentarios
    private List<RequerimientoDto> requerimientosRelacionados; // DTO para traer el objeto completo y mostrarlo
    private List<String> requerimientosRelacionadosCodigos; // Para la vinculacion con otros requerimientos, SOLO para vincular.
}
