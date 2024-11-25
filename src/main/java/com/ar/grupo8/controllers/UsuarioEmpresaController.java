package com.ar.grupo8.controllers;

import com.ar.grupo8.dto.UsuarioEmpresaDto;
import com.ar.grupo8.models.UsuarioEmpresa;
import com.ar.grupo8.service.UsuarioEmpresaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/usuarios")
public class UsuarioEmpresaController {

    @Autowired
    private UsuarioEmpresaService usuarioEmpresaService;
    private UsuarioEmpresaDto usuarioEmpresaDto;

    @GetMapping
    public List<UsuarioEmpresa> getAll() {
        return usuarioEmpresaService.getUsuariosEmpresa();
    }

    @GetMapping("/{id}")
    public Optional<UsuarioEmpresa> getById(@PathVariable("id") Long id) {
        return usuarioEmpresaService.getUsuarioEmpresaById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@Valid @RequestBody UsuarioEmpresaDto usuarioEmpresaDto) {
        usuarioEmpresaService.createUsuarioEmpresa(usuarioEmpresaDto);
        return ResponseEntity.ok("Usuario creado con éxito");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @Valid @RequestBody UsuarioEmpresaDto usuarioEmpresaDto) {
            usuarioEmpresaService.updateUsuarioEmpresa(id, usuarioEmpresaDto);
            return ResponseEntity.ok("Usuario actualizado con éxito");
    }


    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        usuarioEmpresaService.deleteUsuarioEmpresa(id);
    }

}
