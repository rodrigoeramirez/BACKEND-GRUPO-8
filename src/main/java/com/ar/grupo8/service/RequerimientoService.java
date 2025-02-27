package com.ar.grupo8.service;

import com.ar.grupo8.dto.ArchivoAdjuntoDto;
import com.ar.grupo8.dto.ComentarioRequerimientoDto;
import com.ar.grupo8.dto.RequerimientoDto;
import com.ar.grupo8.dto.UpdateRequerimientoDto;
import com.ar.grupo8.models.*;
import com.ar.grupo8.repository.RequerimientoRepository;
import com.ar.grupo8.repository.TipoRequerimientoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RequerimientoService {
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private RequerimientoRepository requerimientoRepository;
    @Autowired
    private TipoRequerimientoRepository tipoRequerimientoRepository;

    public List<RequerimientoDto> getRequerimientos() {
        // Obtener todos los requerimientos
        List<Requerimiento> requerimientos = requerimientoRepository.findAllByActivoTrue();

        // Mapear cada requerimiento a un DTO
        return requerimientos.stream()
                .map(this::mapearARequerimientoDto) // Convertir cada entidad a DTO
                .toList(); // Devolver la lista de DTOs
    }

    private RequerimientoDto mapearARequerimientoDto(Requerimiento requerimiento) {
        RequerimientoDto dto = new RequerimientoDto();
        dto.setCodigo(requerimiento.getCodigo());
        dto.setFechaHoraAlta(requerimiento.getFechaHoraAlta());
        dto.setAsunto(requerimiento.getAsunto());
        dto.setNombreCompletoEmisor(String.format("%s %s", requerimiento.getEmisor().getNombre(), requerimiento.getEmisor().getApellido()));
        dto.setDescripcion(requerimiento.getDescripcion());
        dto.setTipoRequerimientoId(
                requerimiento.getTipoRequerimiento() != null ? requerimiento.getTipoRequerimiento().getId() : null
        );
        dto.setTipoRequerimientoDescripcion(
                requerimiento.getTipoRequerimiento() != null ? requerimiento.getTipoRequerimiento().getDescripcion() : null
        );
        // Obtener la primera categoría asociada al tipo
        List<CategoriaRequerimiento> categorias = requerimiento.getTipoRequerimiento().getCategorias();
        if (categorias != null && !categorias.isEmpty()) {
            dto.setCategoriaRequerimientoDescripcion(categorias.get(0).getDescripcion());
            dto.setCategoriaRequerimientoId(categorias.get(0).getId());
        }
        dto.setEstadoRequerimientoId(
                requerimiento.getEstadoRequerimiento() != null ? requerimiento.getEstadoRequerimiento().getId() : null
        );
        dto.setEstadoRequerimientoNombre(
                requerimiento.getEstadoRequerimiento() != null ? requerimiento.getEstadoRequerimiento().getNombre() : null
        );
        dto.setPrioridadRequerimientoId(
                requerimiento.getPrioridadRequerimiento() != null ? requerimiento.getPrioridadRequerimiento().getId() : null
        );
        dto.setPrioridadRequerimientoNombre(
                requerimiento.getPrioridadRequerimiento() != null ? requerimiento.getPrioridadRequerimiento().getNombre() : null
        );
        dto.setEmisorLegajo((requerimiento.getEmisor().getLegajo()));
        dto.setNombreCompletoEmisor(requerimiento.getEmisor().getNombre()+" "+requerimiento.getEmisor().getApellido());
        dto.setDestinatarioId((requerimiento.getDestinatario() != null ? requerimiento.getDestinatario().getLegajo() : null));
        dto.setNombreCompletoDestinatario((requerimiento.getDestinatario() != null ?requerimiento.getDestinatario().getNombre()+" "+requerimiento.getDestinatario().getApellido(): null));
        // lógica para convertir comentarios y archivos adjuntos si es necesario
        dto.setArchivosAdjuntos(
                requerimiento.getArchivosAdjuntos().stream()
                        .filter(ArchivoAdjunto::getActivo) // Solo me filtra los archivos activos del requerimiento.
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


        dto.setComentarios(
                requerimiento.getComentarios().stream()
                        .map(comentario -> {
                            ComentarioRequerimientoDto comentarioDto = new ComentarioRequerimientoDto();
                            comentarioDto.setAsunto(comentario.getAsunto());
                            comentarioDto.setDescripcion(comentario.getDescripcion());
                            comentarioDto.setUsuarioEmisorId(comentario.getUsuarioEmisor().getLegajo());
                            comentarioDto.setRequerimientoCodigo(comentario.getRequerimiento().getCodigo());
                            comentarioDto.setFechaHora(comentario.getFechaHora());
                            return comentarioDto;
                        }).toList()
        );

        dto.setRequerimientosRelacionados(
                requerimiento.getRequerimientosRelacionados().stream()
                        .filter(Requerimiento::getActivo) // Solo mostramos requerimientos activos
                        .map(req -> {
                            RequerimientoDto requerimientoDtoRelacionado = new RequerimientoDto();
                            requerimientoDtoRelacionado.setCodigo(req.getCodigo());
                            requerimientoDtoRelacionado.setFechaHoraAlta(req.getFechaHoraAlta());
                            requerimientoDtoRelacionado.setAsunto(req.getAsunto());
                            requerimientoDtoRelacionado.setDescripcion(req.getDescripcion());
                            requerimientoDtoRelacionado.setTipoRequerimientoId(req.getTipoRequerimiento().getId());
                            requerimientoDtoRelacionado.setEstadoRequerimientoId(req.getEstadoRequerimiento().getId());
                            requerimientoDtoRelacionado.setPrioridadRequerimientoId(req.getPrioridadRequerimiento().getId());
                            requerimientoDtoRelacionado.setEmisorLegajo(req.getEmisor().getLegajo());
                            requerimientoDtoRelacionado.setEstadoRequerimientoNombre(req.getEstadoRequerimiento().getNombre());
                            requerimientoDtoRelacionado.setPrioridadRequerimientoNombre(req.getPrioridadRequerimiento().getNombre());
                            requerimientoDtoRelacionado.setNombreCompletoDestinatario((req.getDestinatario() != null ? req.getDestinatario().getNombre(): null));
                            return requerimientoDtoRelacionado;

                        }).toList()
        );

        return dto;
    }

    // Transactional: Si hay un error en cualquier parte del proceso, los cambios no se guardan parcialmente.
    @Transactional
    public void updateRequerimiento(String codigo, UpdateRequerimientoDto updateRequerimientoDto, List<MultipartFile> archivos) {
        // Buscar el requerimiento existente
        Requerimiento requerimientoExistente = requerimientoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Requerimiento no encontrado con Código: " + codigo));

        // Actualizar campos básicos si están presentes en el DTO
        if (updateRequerimientoDto.getCodigo() != null) {
            requerimientoExistente.setCodigo(updateRequerimientoDto.getCodigo());
        }
        if (updateRequerimientoDto.getAsunto() != null) {
            requerimientoExistente.setAsunto(updateRequerimientoDto.getAsunto());
        }
        if (updateRequerimientoDto.getDescripcion() != null) {
            requerimientoExistente.setDescripcion(updateRequerimientoDto.getDescripcion());
        }

        if (updateRequerimientoDto.getDestinatarioId() == null) {
            requerimientoExistente.setDestinatario(null);
        } else {
            UsuarioEmpresa destinatario = new UsuarioEmpresa();
            destinatario.setLegajo(updateRequerimientoDto.getDestinatarioId());
            requerimientoExistente.setDestinatario(destinatario);
        }

        if (updateRequerimientoDto.getTipoRequerimientoId() != null) {
            TipoRequerimiento tipoRequerimiento = new TipoRequerimiento();
            tipoRequerimiento.setId(updateRequerimientoDto.getTipoRequerimientoId());
            requerimientoExistente.setTipoRequerimiento(tipoRequerimiento);
        }
        if (updateRequerimientoDto.getEstadoRequerimientoId() != null) {
            EstadoRequerimiento estadoRequerimiento = new EstadoRequerimiento();
            estadoRequerimiento.setId(updateRequerimientoDto.getEstadoRequerimientoId());
            requerimientoExistente.setEstadoRequerimiento(estadoRequerimiento);
        }
        if (updateRequerimientoDto.getPrioridadRequerimientoId() != null) {
            PrioridadRequerimiento prioridadRequerimiento = new PrioridadRequerimiento();
            prioridadRequerimiento.setId(updateRequerimientoDto.getPrioridadRequerimientoId());
            requerimientoExistente.setPrioridadRequerimiento(prioridadRequerimiento);
        }

        //  ACTUALIZAR VINCULACIÓN DE REQUERIMIENTOS RELACIONADOS
        if (updateRequerimientoDto.getRequerimientosRelacionadosCodigos() != null) {
            Set<Requerimiento> nuevosRelacionados = updateRequerimientoDto.getRequerimientosRelacionadosCodigos().stream()
                    .map(cod -> requerimientoRepository.findByCodigo(cod).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            //  1. Elimino relaciones antiguas que ya no deben existir.
            Set<Requerimiento> requerimientosRelacionadosAnteriores = new HashSet<>(requerimientoExistente.getRequerimientosRelacionados());
            for (Requerimiento anterior : requerimientosRelacionadosAnteriores) {
                if (!nuevosRelacionados.contains(anterior)) {
                    anterior.getRequerimientosRelacionados().remove(requerimientoExistente);
                    requerimientoRepository.save(anterior); // Actualizamos el otro lado de la relación
                }
            }

            //  2. Agrego nuevas relaciones asegurando bidireccionalidad.
            for (Requerimiento nuevo : nuevosRelacionados) {
                if (!nuevo.getRequerimientosRelacionados().contains(requerimientoExistente)) {
                    nuevo.getRequerimientosRelacionados().add(requerimientoExistente);
                    requerimientoRepository.save(nuevo); // Guardamos el otro lado de la relación
                }
            }

            //  3. Actualizar la lista de relacionados del requerimiento actual.
            requerimientoExistente.setRequerimientosRelacionados(new ArrayList<>(nuevosRelacionados));
        }

        //  Mantenimiento de archivos existentes (sin eliminarlos)
        List<ArchivoAdjunto> archivosAdjuntosActuales = requerimientoExistente.getArchivosAdjuntos();

        // Agregar nuevos archivos sin eliminar los anteriores
        if (archivos != null && !archivos.isEmpty()) {
            for (MultipartFile archivo : archivos) {
                String rutaArchivo = fileStorageService.storeFile(archivo);
                ArchivoAdjunto archivoAdjunto = new ArchivoAdjunto();
                archivoAdjunto.setActivo(true);
                archivoAdjunto.setNombreOriginal(archivo.getOriginalFilename());
                archivoAdjunto.setRuta(rutaArchivo);
                archivoAdjunto.setRequerimiento(requerimientoExistente);
                archivosAdjuntosActuales.add(archivoAdjunto);
            }
        }

        requerimientoExistente.setArchivosAdjuntos(archivosAdjuntosActuales);

        // Guardar los cambios en la base de datos
        requerimientoRepository.save(requerimientoExistente);
    }

    private Requerimiento mapearARequerimiento(RequerimientoDto requerimientoDto) {
        Requerimiento newRequerimiento = new Requerimiento();
        newRequerimiento.setActivo(true);
        newRequerimiento.setAsunto(requerimientoDto.getAsunto());
        newRequerimiento.setDescripcion(requerimientoDto.getDescripcion());
        newRequerimiento.setCodigo(requerimientoDto.getCodigo());

        TipoRequerimiento tipoRequerimiento = new TipoRequerimiento();
        tipoRequerimiento.setId(requerimientoDto.getTipoRequerimientoId());
        newRequerimiento.setTipoRequerimiento(tipoRequerimiento);

        PrioridadRequerimiento prioridad = new PrioridadRequerimiento();
        prioridad.setId(requerimientoDto.getPrioridadRequerimientoId());
        newRequerimiento.setPrioridadRequerimiento(prioridad);

        EstadoRequerimiento estado = new EstadoRequerimiento();
        estado.setId(requerimientoDto.getEstadoRequerimientoId());
        newRequerimiento.setEstadoRequerimiento(estado);

        UsuarioEmpresa usuarioEmisor = new UsuarioEmpresa();
        usuarioEmisor.setLegajo(requerimientoDto.getEmisorLegajo());
        newRequerimiento.setEmisor(usuarioEmisor);

        if (requerimientoDto.getDestinatarioId() != null) {
            UsuarioEmpresa usuarioDestinatario = new UsuarioEmpresa();
            usuarioDestinatario.setLegajo(requerimientoDto.getDestinatarioId());
            newRequerimiento.setDestinatario(usuarioDestinatario);
        }

        // NO seteo archivos adjuntos acá porque se manejan en createRequerimiento.
        newRequerimiento.setArchivosAdjuntos(new ArrayList<>()); // Lista vacía para que luego se setee en createRequerimiento

        newRequerimiento.setComentarios(new ArrayList<>()); // Lista vacía para comentarios.

        // Si hay requerimientos relacionados en requerimientoDto.getRequerimientosRelacionadosCodigos(), se buscan en la base de datos y se almacenan en un Set para evitar duplicados.
        Set<Requerimiento> requerimientosRelacionados = new HashSet<>(); //

        if (requerimientoDto.getRequerimientosRelacionadosCodigos() != null &&
                !requerimientoDto.getRequerimientosRelacionadosCodigos().isEmpty()) {

            requerimientosRelacionados = requerimientoDto.getRequerimientosRelacionadosCodigos().stream()
                    .map(codigo -> requerimientoRepository.findByCodigo(codigo).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            //  Importante: NO agregar relaciones acá, ya que newRequerimiento aún no se ha guardado.
            // newRequerimiento aún no tiene ID porque no fue guardado en la base de datos.
        }

        // NO guardo los requerimientos relacionados acá, lo hacemos en createRequerimiento
        newRequerimiento.setRequerimientosRelacionados(new ArrayList<>(requerimientosRelacionados));

        return newRequerimiento;
    }

    // Transactional: Si hay un error en cualquier parte del proceso, los cambios no se guardan parcialmente.
    @Transactional
    public Requerimiento createRequerimiento(RequerimientoDto requerimientoDto, List<MultipartFile> archivos) {
        // Primero guardo el requerimiento sin relaciones vinculadas, ni archivos adjuntos.
        Requerimiento newRequerimiento = requerimientoRepository.save(mapearARequerimiento(requerimientoDto));

        // Luego agrego las relaciones vinculadas y actualizo.
        if (requerimientoDto.getRequerimientosRelacionadosCodigos() != null &&
                !requerimientoDto.getRequerimientosRelacionadosCodigos().isEmpty()) {

            // Se buscan los requerimientos relacionados y se actualizan ambos lados de la relación:
            Set<Requerimiento> requerimientosRelacionados = requerimientoDto.getRequerimientosRelacionadosCodigos().stream()
                    .map(codigo -> requerimientoRepository.findByCodigo(codigo).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            for (Requerimiento relacionado : requerimientosRelacionados) {
                if (!relacionado.getRequerimientosRelacionados().contains(newRequerimiento)) {
                    relacionado.getRequerimientosRelacionados().add(newRequerimiento);
                    requerimientoRepository.save(relacionado); // Guardo la relación en la base de datos
                }
            }
            newRequerimiento.setRequerimientosRelacionados(new ArrayList<>(requerimientosRelacionados));
            requerimientoRepository.save(newRequerimiento);
        }

        // Ahora guardo los archivos adjuntos(si existen)
        if (archivos != null && !archivos.isEmpty()) {
            List<ArchivoAdjunto> archivosAdjuntos = new ArrayList<>();

            for (MultipartFile archivo : archivos) {
                String rutaArchivo = fileStorageService.storeFile(archivo);
                ArchivoAdjunto archivoAdjunto = new ArchivoAdjunto();
                archivoAdjunto.setActivo(true);
                archivoAdjunto.setNombreOriginal(archivo.getOriginalFilename());
                archivoAdjunto.setRuta(rutaArchivo);
                archivoAdjunto.setRequerimiento(newRequerimiento);
                archivosAdjuntos.add(archivoAdjunto);
            }

            newRequerimiento.getArchivosAdjuntos().addAll(archivosAdjuntos);
            requerimientoRepository.save(newRequerimiento);
        }

        return newRequerimiento;
    }

    public void deleteRequerimiento(String codigo){
        Requerimiento requerimiento = requerimientoRepository.findByCodigo(codigo)
                .orElseThrow(()->new RuntimeException("Requerimiento no encontrado"));
        requerimiento.setActivo(false);

        requerimientoRepository.save(requerimiento);
    }

    public int obtenerUltimoSecuencial(Long tipoRequerimientoId) {
        // Obtener el prefijo del tipo de requerimiento
        TipoRequerimiento tipo = tipoRequerimientoRepository.findById(tipoRequerimientoId)
                .orElseThrow(() -> new RuntimeException("Tipo de requerimiento no encontrado"));
        String prefijoTipo = tipo.getCodigo(); // Obtener el prefijo del tipo

        // Obtener el año actual
        int anioActual = Year.now().getValue();

        // Obtener todos los códigos existentes para el tipo y año actual
        List<Requerimiento> requerimientos = requerimientoRepository.findAllByTipoRequerimientoIdAndCodigoLike(
                tipoRequerimientoId, prefijoTipo + "-" + anioActual + "-%");

        // Encontrar el máximo secuencial
        int maxSecuencial = 0;
        for (Requerimiento req : requerimientos) {
            String codigo = req.getCodigo();

            // Extraer los últimos 10 dígitos del código
            String secuencialStr = codigo.substring(codigo.lastIndexOf("-") + 1);

            // Verificar que el secuencialStr tenga exactamente 10 dígitos
            if (secuencialStr.length() == 10) {
                try {
                    int secuencial = Integer.parseInt(secuencialStr);
                    if (secuencial > maxSecuencial) {
                        maxSecuencial = secuencial;
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Error al convertir el secuencial a número: " + secuencialStr);
                }
            } else {
                System.err.println("Formato de secuencial incorrecto: " + secuencialStr);
            }
        }

        // Retornar el siguiente secuencial
        return maxSecuencial + 1;
    }

}
