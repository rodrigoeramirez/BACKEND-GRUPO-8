package com.ar.grupo8.controllers;

import com.ar.grupo8.models.Departamento;
import com.ar.grupo8.service.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="/departamentos")
public class DepartamentoController {
    @Autowired
    private DepartamentoService departamentoService;

    @GetMapping
    public List<Departamento> getDepartamentos() {
    return departamentoService.getDepartamentos();
    }
}
