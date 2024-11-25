package com.ar.grupo8.service;
import com.ar.grupo8.dto.UsuarioEmpresaDto;
import com.ar.grupo8.models.Cargo;
import com.ar.grupo8.models.Departamento;
import com.ar.grupo8.models.UsuarioEmpresa;
import com.ar.grupo8.repository.UsuarioEmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UsuarioEmpresaService {
    @Autowired // Le dice a Spring que debe inyectar automáticamente una dependencia (en este caso, UsuarioEmpresaRepository)
    // En lugar de crear manualmente una instancia del repositorio (por ejemplo, new UsuarioEmpresaRepository()), Spring se encarga de gestionarlo.
    UsuarioEmpresaRepository usuarioEmpresaRepository; // Defino el repositorio.

    public List<UsuarioEmpresa> getUsuariosEmpresa () {
        return usuarioEmpresaRepository.findAll();
    }

    public Optional<UsuarioEmpresa> getUsuarioEmpresaById (Long id) {
        return usuarioEmpresaRepository.findById(id);
    }

    public void createUsuarioEmpresa (UsuarioEmpresaDto usuarioEmpresaDTO) {
        UsuarioEmpresa usuarioEmpresa = new UsuarioEmpresa();

        usuarioEmpresa.setNombre(usuarioEmpresaDTO.getNombre());
        usuarioEmpresa.setApellido(usuarioEmpresaDTO.getApellido());
        usuarioEmpresa.setUsername(usuarioEmpresaDTO.getUsername());
        usuarioEmpresa.setClave(usuarioEmpresaDTO.getClave());
        usuarioEmpresa.setEmail(usuarioEmpresaDTO.getEmail());
        usuarioEmpresa.setLegajo(usuarioEmpresaDTO.getLegajo());

        // En usuarioEmpresaDTO me llegan los id para la foranea, sin embargo, en el servicio (UsuarioEmpresaService), necesito mapear estos IDs a los objetos Departamento y Cargo correspondientes.
        Departamento departamento = new Departamento();
        departamento.setId(usuarioEmpresaDTO.getDepartamento_id());
        usuarioEmpresa.setDepartamento(departamento);

        Cargo cargo = new Cargo();
        cargo.setId(usuarioEmpresaDTO.getCargo_id());
        usuarioEmpresa.setCargo(cargo);

        // Guardar el objeto UsuarioEmpresa en la base de datos
        usuarioEmpresaRepository.save(usuarioEmpresa);
    }

    public void updateUsuarioEmpresa(Long id, UsuarioEmpresaDto usuarioEmpresaDto) {
        // Buscar el usuario por id
        Optional<UsuarioEmpresa> usuarioEmpresaOptional = usuarioEmpresaRepository.findById(id);
        if (usuarioEmpresaOptional.isEmpty()) {
            throw new NoSuchElementException("UsuarioEmpresa no encontrado con id: " + id);
        }

        UsuarioEmpresa usuarioEmpresa = usuarioEmpresaOptional.get();

        // Actualizar los campos del usuario
        usuarioEmpresa.setNombre(usuarioEmpresaDto.getNombre());
        usuarioEmpresa.setApellido(usuarioEmpresaDto.getApellido());
        usuarioEmpresa.setUsername(usuarioEmpresaDto.getUsername());
        usuarioEmpresa.setClave(usuarioEmpresaDto.getClave());
        usuarioEmpresa.setEmail(usuarioEmpresaDto.getEmail());
        usuarioEmpresa.setLegajo(usuarioEmpresaDto.getLegajo());

        // Asignar el id del departamento y cargo directamente
        Departamento departamento = new Departamento();
        departamento.setId(usuarioEmpresaDto.getDepartamento_id()); // Asignar el id al departamento

        Cargo cargo = new Cargo();
        cargo.setId(usuarioEmpresaDto.getCargo_id()); // Asignar el id al cargo

        // Establecer las relaciones
        usuarioEmpresa.setDepartamento(departamento);
        usuarioEmpresa.setCargo(cargo);

        // Guardar el usuario actualizado
        usuarioEmpresaRepository.save(usuarioEmpresa);
    }

    public void deleteUsuarioEmpresa (Long id) {
        usuarioEmpresaRepository.deleteById(id);
    }
}
