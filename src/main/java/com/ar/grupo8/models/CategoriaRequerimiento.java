package com.ar.grupo8.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="categoria_requerimiento")
public class CategoriaRequerimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="descripcion", nullable = false)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "tipo_requerimiento_id", nullable = false)
    @JsonBackReference // Evita la recursión desde esta dirección
    private TipoRequerimiento tipo_requerimiento;

}
