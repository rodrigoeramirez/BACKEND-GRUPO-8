package com.ar.grupo8.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "comentario_requerimiento")
public class ComentarioRequerimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "asunto", nullable = false, length = 50)
    private String asunto; // Asunto del comentario

    @Column(name = "descripcion", nullable = false, length = 5000)
    private String descripcion; // Descripcion del comentario


    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora = LocalDateTime.now(); // Fecha y hora del comentario

    @ManyToOne
    @JoinColumn(name = "usuario_emisor_id", nullable = false)
    private UsuarioEmpresa usuarioEmisor; // usuarioEmisor asociado a este comentario

    @ManyToOne
    @JoinColumn(name = "requerimiento_id", nullable = false)
    private Requerimiento requerimiento; // Requerimiento asociado a este comentario

    @OneToMany(mappedBy = "comentario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArchivoAdjunto> archivosAdjuntos = new ArrayList<>(); // Archivos adjuntos del comentario
}
