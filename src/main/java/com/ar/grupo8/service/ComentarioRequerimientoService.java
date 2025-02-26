package com.ar.grupo8.service;

import com.ar.grupo8.dto.ArchivoAdjuntoDto;
import com.ar.grupo8.dto.ComentarioRequerimientoDto;
import com.ar.grupo8.models.ArchivoAdjunto;
import com.ar.grupo8.models.ComentarioRequerimiento;
import com.ar.grupo8.models.Requerimiento;
import com.ar.grupo8.models.UsuarioEmpresa;
import com.ar.grupo8.repository.ArchivoAdjuntoRepository;
import com.ar.grupo8.repository.ComentarioRequerimientoRepository;
import com.ar.grupo8.repository.RequerimientoRepository;
import com.ar.grupo8.repository.UsuarioEmpresaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ComentarioRequerimientoService {
    @Autowired
    ComentarioRequerimientoRepository comentarioRequerimientoRepository;
    @Autowired
    RequerimientoRepository requerimientoRepository;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    UsuarioEmpresaRepository usuarioEmpresaRepository;
    @Autowired
    ArchivoAdjuntoRepository archivoAdjuntoRepository;


    private ComentarioRequerimientoDto mapearAComentarioRequerimientoDto (ComentarioRequerimiento comentario) {
        ComentarioRequerimientoDto dto = new ComentarioRequerimientoDto();
        dto.setRequerimientoCodigo(comentario.getRequerimiento().getCodigo());
        dto.setDescripcion(comentario.getDescripcion());
        dto.setAsunto(comentario.getAsunto());
        dto.setFechaHora(comentario.getFechaHora());
        dto.setUsername(comentario.getUsuarioEmisor().getUsername());
        dto.setUsuarioEmisorId(comentario.getUsuarioEmisor().getLegajo());
        dto.setArchivosAdjuntos(
                comentario.getArchivosAdjuntos().stream()
                        .filter(ArchivoAdjunto::getActivo) // Solo me filtra los archivos activos del comentario.
                        .map(archivo -> {
                            ArchivoAdjuntoDto archivoDto = new ArchivoAdjuntoDto();
                            archivoDto.setActivo(archivo.getActivo());
                            archivoDto.setId(archivo.getId());
                            archivoDto.setNombreOriginal(archivo.getNombreOriginal());
                            archivoDto.setRuta(archivo.getRuta());
                            return archivoDto;
                        })
                        .toList()
        );
        return dto;
    }

    public List<ComentarioRequerimientoDto> getComentariosByRequerimientoId(String codigo) {
        try {
            Optional<Requerimiento> requerimiento = requerimientoRepository.findByCodigo(codigo); // Obtengo el requerimiento porque el id no lo peudo obtener desde el front.
            List<ComentarioRequerimiento> comentarios = comentarioRequerimientoRepository.findByRequerimientoId(requerimiento.get().getId());
            if (comentarios != null) {
                return comentarios.stream()
                        .map(this::mapearAComentarioRequerimientoDto)
                        .toList();
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ComentarioRequerimiento mapearAComentarioRequerimiento (ComentarioRequerimientoDto comentarioDto){
        ComentarioRequerimiento newComentario = new ComentarioRequerimiento();
        newComentario.setAsunto(comentarioDto.getAsunto());
        newComentario.setDescripcion(comentarioDto.getDescripcion());
        newComentario.setFechaHora(comentarioDto.getFechaHora());

        UsuarioEmpresa emisor = usuarioEmpresaRepository.findByLegajo(comentarioDto.getUsuarioEmisorId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        newComentario.setUsuarioEmisor(emisor);

        Requerimiento requerimiento = requerimientoRepository.findByCodigo(comentarioDto.getRequerimientoCodigo())
                .orElseThrow(() -> new RuntimeException("Requerimiento no encontrado"));
        newComentario.setRequerimiento(requerimiento);


        // NO seteo archivos adjuntos acá porque se manejan en createComentario.
        newComentario.setArchivosAdjuntos(new ArrayList<>()); // Lista vacía para que luego se setee en createComentario.

        return newComentario;
    }

    @Transactional
    public ComentarioRequerimiento createComentarioRequerimiento(ComentarioRequerimientoDto comentarioRequerimientoDto, List<MultipartFile> archivos) {
        ComentarioRequerimiento newComentario = mapearAComentarioRequerimiento(comentarioRequerimientoDto);

        // Guardo el comentario primero para obtener su ID.
        ComentarioRequerimiento comentarioGuardado = comentarioRequerimientoRepository.save(newComentario);

        // Si hay archivos adjuntos, los agregodespués de guardar el comentario.
        if (archivos != null && !archivos.isEmpty()) {
            List<ArchivoAdjunto> archivosAdjuntos = new ArrayList<>();

            for (MultipartFile archivo : archivos) {
                String rutaArchivo = fileStorageService.storeFile(archivo);
                ArchivoAdjunto archivoAdjunto = new ArchivoAdjunto();
                archivoAdjunto.setActivo(true);
                archivoAdjunto.setNombreOriginal(archivo.getOriginalFilename());
                archivoAdjunto.setRuta(rutaArchivo);
                archivoAdjunto.setComentario(comentarioGuardado);
                archivosAdjuntos.add(archivoAdjunto);
            }

            archivoAdjuntoRepository.saveAll(archivosAdjuntos);
            comentarioGuardado.getArchivosAdjuntos().addAll(archivosAdjuntos);
        }

        return comentarioGuardado;
    }

}
