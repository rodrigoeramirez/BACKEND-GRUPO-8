package com.ar.grupo8.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UpdateRequerimientoDto {

    private String codigo; // Código puede actualizarse pero no es obligatorio.

    private LocalDateTime fechaHoraAlta; // Fecha y hora pueden ser opcionales.

    private String asunto; // Asunto opcional.

    private String descripcion; // Descripción opcional.

    private Long tipoRequerimientoId; // ID del tipo de requerimiento, opcional.

    private Long estadoRequerimientoId; // ID del estado del requerimiento, opcional.

    private Long prioridadRequerimientoId; // ID de la prioridad, opcional.

    private Integer destinatarioId; // ID del destinatario, opcional.

    private Integer emisorLegajo; // Emisor, opcional en caso de actualizaciones.

    private List<ArchivoAdjuntoDto> archivosAdjuntos; // Archivos adjuntos opcionales.

    private List<ComentarioRequerimientoDto> comentarios; // Comentarios opcionales.

    private List<String> requerimientosRelacionadosCodigos; // Para la vinculacion con otros requerimientos, SOLO para vincular.
}
