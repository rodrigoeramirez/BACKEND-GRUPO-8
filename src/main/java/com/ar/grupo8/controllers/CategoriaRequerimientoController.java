package com.ar.grupo8.controllers;

import com.ar.grupo8.models.CategoriaRequerimiento;
import com.ar.grupo8.service.CategoriaRequerimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="categoria_requerimiento")
public class CategoriaRequerimientoController {
    @Autowired
    private CategoriaRequerimientoService categoriaRequerimientoService;

    @GetMapping
    public List<CategoriaRequerimiento> getCategoriaRequerimiento () {
        return categoriaRequerimientoService.getCategoriaRequerimiento();
    }
}
