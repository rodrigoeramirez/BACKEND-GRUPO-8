package com.ar.grupo8.models;
import jakarta.persistence.*;
import lombok.Data;

@Data // Genera automaticamente los Getters y Setters sin que tengas que escribirlo manualmente.
@Entity // Marca una clase como una entidad de JPA, lo que significa que esta clase se va a mapear a una tabla en la base de datos.
@Table(name="usuario_empresa") // Especifica el nombre de la tabla en la base de datos a la que se va a mapear la entidad.
public class UsuarioEmpresa {

    @Id // Esto refeleja que va a ser un Id.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Se va a generar de forma automatica.
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "clave", nullable = false)
    private String clave;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "legajo", nullable = false)
    private String legajo;

    @Column(name = "activo", nullable = false)
    private Boolean activo=true; // Por defecto el usuario va a estar activo.

    // Relación con Departamento
    @ManyToOne // Muchos usuarios pueden pertenecer a un departamento.
    @JoinColumn(name = "departamento_id", nullable = false) // Define el nombre de la columna en la base de datos que actuará como clave foránea para esta relación.
    private Departamento departamento;

    // Relación con Cargo
    @ManyToOne // Muchos usuarios pueden tener un cargo.
    @JoinColumn(name = "cargo_id", nullable = false) // Nombre de la columna en la tabla.
    private Cargo cargo; // Es un atributo de la clase UsuarioEmpresa que representa el objeto Departamento al que está asociado el usuario.
    // Este campo no guarda directamente el ID del departamento (eso lo hace la base de datos).
    // En su lugar, JPA maneja automáticamente las relaciones y te permite accede al objeto completo.
}
