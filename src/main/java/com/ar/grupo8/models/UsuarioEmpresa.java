package com.ar.grupo8.models;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data // Genera automaticamente los Getters y Setters sin que tengas que escribirlo manualmente.
@Entity // Marca una clase como una entidad de JPA, lo que significa que esta clase se va a mapear a una tabla en la base de datos.
@Table // Especifica el nombre de la tabla en la base de datos a la que se va a mapear la entidad.
public class UsuarioEmpresa {

}
