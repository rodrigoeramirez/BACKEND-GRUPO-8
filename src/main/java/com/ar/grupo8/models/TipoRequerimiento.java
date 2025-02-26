package com.ar.grupo8.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table
public class TipoRequerimiento {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo", nullable = false, length = 3, unique = true)
    private String codigo;

    @Column (name ="descripcion", nullable = false)
    private String descripcion;

    @OneToMany(mappedBy = "tipo_requerimiento", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Marca el propietario de la relación
    private List<CategoriaRequerimiento> categorias;

}
