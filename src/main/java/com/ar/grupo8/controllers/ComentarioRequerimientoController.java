package com.ar.grupo8.controllers;

import com.ar.grupo8.dto.ComentarioRequerimientoDto;
import com.ar.grupo8.dto.RequerimientoDto;
import com.ar.grupo8.models.ComentarioRequerimiento;
import com.ar.grupo8.models.Requerimiento;
import com.ar.grupo8.service.ComentarioRequerimientoService;
import com.ar.grupo8.service.RequerimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path="/comentarios")
public class ComentarioRequerimientoController {
    @Autowired
    private ComentarioRequerimientoService comentarioRequerimientoService;
    @Autowired
    private RequerimientoService requerimientoService;

    @GetMapping("/{codigo}")
    public List<ComentarioRequerimientoDto> getComentarioByRequerimientoId (@PathVariable("codigo") String codigo) {
        try {
            return comentarioRequerimientoService.getComentariosByRequerimientoId(codigo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createComentarioRequerimiento(
            @RequestPart("comentario") ComentarioRequerimientoDto comentarioRequerimientoDto,
            @RequestPart(value = "archivos", required = false) List<MultipartFile> archivos) {
        try {

            ComentarioRequerimiento comentarioCreado = comentarioRequerimientoService.createComentarioRequerimiento(comentarioRequerimientoDto, archivos);
            return ResponseEntity.status(HttpStatus.CREATED).body(comentarioCreado);
        } catch (Exception e) {
            e.printStackTrace(); //  IMPRIMIR ERROR EN CONSOLA
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error en el servidor: " + e.getMessage()); //  DEVOLVER ERROR EXPLICATIVO
        }
    }
}
