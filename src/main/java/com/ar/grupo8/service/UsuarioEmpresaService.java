package com.ar.grupo8.service;
import com.ar.grupo8.models.UsuarioEmpresa;
import com.ar.grupo8.repository.UsuarioEmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public void createUsuarioEmpresa (UsuarioEmpresa usuarioEmpresa) {
        usuarioEmpresaRepository.save(usuarioEmpresa);
    }

    public void deleteUsuarioEmpresa (Long id) {
        usuarioEmpresaRepository.deleteById(id);
    }
}
