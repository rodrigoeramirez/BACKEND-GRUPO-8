package com.ar.grupo8.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "archivo_adjunto")
public class ArchivoAdjunto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    @Column(name="nombre_original", nullable = false)
    private String nombreOriginal;

    // Esto es clave, la RUTA almacena la ubicación del archivo en el servidor.
    @Column(name="ruta", nullable = false)
    private String ruta;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "comentario_id")
    private ComentarioRequerimiento comentario; // Comentario asociado al archivo

    @ManyToOne
    @JoinColumn(name = "requerimiento_id")
    private Requerimiento requerimiento;
}
