package com.ar.grupo8.controllers;

import com.ar.grupo8.models.EstadoRequerimiento;
import com.ar.grupo8.service.EstadoRequerimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="/estado_requerimiento")
public class EstadoRequerimientoController {
    @Autowired
    private EstadoRequerimientoService estadoRequerimientoService;

    @GetMapping
    public List<EstadoRequerimiento> getEstadoRequerimiento() {
        return estadoRequerimientoService.getEstadoRequerimiento();
    }
}
