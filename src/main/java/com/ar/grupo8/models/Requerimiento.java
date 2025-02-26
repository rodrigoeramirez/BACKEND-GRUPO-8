package com.ar.grupo8.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="requerimiento")
public class Requerimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    @Column(name = "codigo", nullable = false, unique = true, length = 20)
    private String codigo; // Este es el codigo genereado automaticamente. Porejemplo: REH-2024-0000000001

    @Column(name="fecha_hora_alta", nullable = false)
    private LocalDateTime fechaHoraAlta = LocalDateTime.now();

    @Column(name="asunto", nullable = false, length = 50)
    private String asunto;

    @Column(name="descripcion", nullable = false, length = 5000)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name="tipo_requerimiento_id", nullable = false)
    private  TipoRequerimiento tipoRequerimiento;

    @ManyToOne
    @JoinColumn(name="estado_requerimiento_id", nullable = false)
    private  EstadoRequerimiento estadoRequerimiento;

    @ManyToOne
    @JoinColumn(name="prioridad_requerimiento_id", nullable = false)
    private  PrioridadRequerimiento prioridadRequerimiento;

    @ManyToOne
    @JoinColumn(name="usuario_destinatario_id")
    private  UsuarioEmpresa destinatario; // El destinatario es opcional (Propietario del caso)

    @ManyToOne
    @JoinColumn(name="usuario_emisor_id", nullable = false)
    private UsuarioEmpresa emisor; // Este es el usaurio que emite el requerimiento.

    // @OneToMany define que un Requerimiento puede tener muchos ArchivoAdjunto asociados.
    // mappedBy = "requerimiento": Indica que esta relación es el lado inverso, es decir, que el propietario de la relación es la propiedad Requerimiento requerimiento definida en el modelo ArchivoAdjunto.
    // Siguiendo con lo de mappedBy = "requerimiento": En otras palabras, este lado no administra la relación directamente; solo la refleja. El control de la relación lo tiene la entidad ArchivoAdjunto.
    // cascade = CascadeType.ALL:
            //Si guardas un Requerimiento que tiene archivos adjuntos nuevos, estos archivos se guardarán automáticamente en la base de datos.
            //Si eliminas un Requerimiento, todos los ArchivoAdjunto relacionados también se eliminarán.
    @OneToMany(mappedBy = "requerimiento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArchivoAdjunto> archivosAdjuntos = new ArrayList<>(); // Archivos adjuntos

    @OneToMany(mappedBy = "requerimiento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ComentarioRequerimiento> comentarios = new ArrayList<>();


    @ManyToMany
    @JoinTable(
            name = "requerimiento_relacionado",
            joinColumns = @JoinColumn(name = "requerimiento_id"),
            inverseJoinColumns = @JoinColumn(name = "relacionado_id")
    )
    private List<Requerimiento> requerimientosRelacionados = new ArrayList<>();

    @ManyToMany(mappedBy = "requerimientosRelacionados") // 🔥 Relación inversa
    private List<Requerimiento> requerimientosQueMeRelacionan = new ArrayList<>();


}
