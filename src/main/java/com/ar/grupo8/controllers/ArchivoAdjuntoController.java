package com.ar.grupo8.controllers;

import com.ar.grupo8.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

@RestController
@RequestMapping("/archivos_adjuntos")
public class ArchivoAdjuntoController {
    @Autowired
    private FileStorageService fileStorageService;
    private final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> descargarArchivo(@PathVariable String filename) {
        try {

            // 🔹 Remover "uploads/" si está en la URL
            String archivo = filename.replace("uploads/", "").trim();

            // 🔹 Construir la ruta absoluta del archivo
            Path filePath = Paths.get(UPLOAD_DIR).resolve(archivo).normalize();
            System.out.println("📂 Buscando archivo en: " + filePath.toString());

            if (!Files.exists(filePath)) {
                System.out.println("⚠️ Archivo no encontrado en: " + filePath.toString());
                return ResponseEntity.notFound().build();
            }

            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                System.out.println("Archivo encontrado y listo para descargar.");
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"") // Lo devuelve como descarga.
                        .body(resource);
            } else {
                return ResponseEntity.status(500).body(null);
            }
        } catch (MalformedURLException e) {
            System.out.println("Error al procesar la URL del archivo.");
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/delete/{id}")
        public ResponseEntity<String> eliminarArchivo(@PathVariable("id") Long id) {
            fileStorageService.eliminarArchivoAdjunto(id);
            return ResponseEntity.ok("Archivo adjunto eliminado con exito");
        }

}


