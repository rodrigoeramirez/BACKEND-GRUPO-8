package com.ar.grupo8.dto;

import lombok.Data;

@Data
public class ArchivoAdjuntoDto {
    private Long id;
    private String nombreOriginal; // Nombre del archivo
    private String ruta; // Ruta o URL donde se almacena el archivo
    //Debe tener asignado un requerimientoId o un comntarioId por lo menos.
    private Long requerimientoId; // Puede ser null
    private Long comentarioId; // Puede ser null
    private Boolean activo; // Es booleano

}
