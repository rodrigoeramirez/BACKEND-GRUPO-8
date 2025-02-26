package com.ar.grupo8.service;

import com.ar.grupo8.models.ArchivoAdjunto;
import com.ar.grupo8.repository.ArchivoAdjuntoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {
    @Autowired
    private ArchivoAdjuntoRepository archivoAdjuntoRepository;
    private final String UPLOAD_DIR = "uploads/";

    //FileStorageService almacena los archivos en la carpeta uploads/
    public String storeFile(MultipartFile file) {
        try {
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR).resolve(fileName);

            // Asegurar que el directorio de uploads existe
            Files.createDirectories(filePath.getParent());

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return filePath.toString();
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el archivo: " + file.getOriginalFilename(), e);
        }
    }

    // Hace el borrado logico
    public void eliminarArchivoAdjunto (Long id) {
        ArchivoAdjunto archivo = archivoAdjuntoRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Archivo adjunto no encontrado"));
        archivo.setActivo(false);

        archivoAdjuntoRepository.save(archivo);
    }
}

