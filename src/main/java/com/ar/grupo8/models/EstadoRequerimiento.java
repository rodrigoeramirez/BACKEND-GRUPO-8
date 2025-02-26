package com.ar.grupo8.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;

@Data
@Entity
@Table(name="estado_requerimiento")
public class EstadoRequerimiento {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long id;

    @Column(name="nombre", nullable = false)
    public String nombre;
}
