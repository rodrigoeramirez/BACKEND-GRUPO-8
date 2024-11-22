package com.ar.grupo8.controllers;

import com.ar.grupo8.models.UsuarioEmpresa;
import com.ar.grupo8.service.UsuarioEmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/usuarios")
public class UsuarioEmpresaController {

    @Autowired
    private UsuarioEmpresaService usuarioEmpresaService;

    @GetMapping
    public List<UsuarioEmpresa> getAll() {
        return usuarioEmpresaService.getUsuariosEmpresa();
    }

    @GetMapping("/{id}")
    public Optional<UsuarioEmpresa> getById(@PathVariable("id") Long id) {
        return usuarioEmpresaService.getUsuarioEmpresaById(id);
    }

    @PostMapping("/create")
    public void create(@RequestBody UsuarioEmpresa usuarioEmpresa) {
        usuarioEmpresaService.createUsuarioEmpresa(usuarioEmpresa);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        usuarioEmpresaService.deleteUsuarioEmpresa(id);
    }

}
