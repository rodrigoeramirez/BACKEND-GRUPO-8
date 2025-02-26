package com.ar.grupo8.controllers;

import com.ar.grupo8.dto.RequerimientoDto;
import com.ar.grupo8.dto.UpdateRequerimientoDto;
import com.ar.grupo8.models.Requerimiento;
import com.ar.grupo8.service.RequerimientoService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping(path = "/requerimiento")
public class RequerimientoController {
    @Autowired
    private RequerimientoService requerimientoService;

    @GetMapping
    public List<RequerimientoDto> getRequerimientos(){
        return requerimientoService.getRequerimientos();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createRequerimiento(
            @RequestPart("requerimiento") RequerimientoDto requerimientoDto,
            @RequestPart(value = "archivos", required = false) List<MultipartFile> archivos) {
        try {
            Requerimiento requerimientoCreado = requerimientoService.createRequerimiento(requerimientoDto, archivos);
            return ResponseEntity.status(HttpStatus.CREATED).body(requerimientoCreado);
        } catch (Exception e) {
            e.printStackTrace(); //  IMPRIMIR ERROR EN CONSOLA
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error en el servidor: " + e.getMessage()); //  DEVOLVER ERROR EXPLICATIVO
        }
    }

    @PatchMapping("/update/{codigo}")
    public ResponseEntity<String> updateRequerimientoPorCodigo(
            @PathVariable String codigo,
            @RequestPart("requerimiento") UpdateRequerimientoDto updateRequerimientoDto,
            @RequestPart(value = "archivos", required = false) List<MultipartFile> archivos) {
        try {
            requerimientoService.updateRequerimiento(codigo, updateRequerimientoDto, archivos);
            return ResponseEntity.ok("Requerimiento actualizado con exito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    @PostMapping("/delete/{codigo}")
    public ResponseEntity<String> deleteRequerimiento(@PathVariable("codigo") String codigo){
        requerimientoService.deleteRequerimiento(codigo);
        return ResponseEntity.ok("Requerimiento eliminado con exito");
    }


    @GetMapping("/ultimo-secuencial/{tipoRequerimientoId}")
    public ResponseEntity<Integer> getUltimoSecuencial(@PathVariable Long tipoRequerimientoId) {
        int secuencial = requerimientoService.obtenerUltimoSecuencial(tipoRequerimientoId);
        return ResponseEntity.ok(secuencial);
    }

}
