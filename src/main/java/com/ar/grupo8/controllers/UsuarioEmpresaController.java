package com.ar.grupo8.controllers;

import com.ar.grupo8.dto.UpdateUsuarioEmpresaDto;
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

    @CrossOrigin(origins = "http://localhost:5173/") // Permite solicitudes desde React
    @GetMapping
    public List<UsuarioEmpresaDto> getAll() {
        return usuarioEmpresaService.getUsuariosEmpresa();
    }

    @GetMapping("/{id}")
    public Optional<UsuarioEmpresaDto> getById(@PathVariable("id") Long id) {
        return usuarioEmpresaService.getUsuarioEmpresaById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@Valid @RequestBody UsuarioEmpresaDto usuarioEmpresaDto) {
        usuarioEmpresaService.createUsuarioEmpresa(usuarioEmpresaDto);
        return ResponseEntity.ok("Usuario creado con éxito");
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @Valid @RequestBody UpdateUsuarioEmpresaDto updateUsuarioEmpresaDto) {
            usuarioEmpresaService.updateUsuarioEmpresa(id, updateUsuarioEmpresaDto);
            return ResponseEntity.ok("Usuario actualizado con éxito");
    }


    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        usuarioEmpresaService.deleteUsuarioEmpresa(id);
    }

}
