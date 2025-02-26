package com.ar.grupo8.controllers;

import com.ar.grupo8.models.PrioridadRequerimiento;
import com.ar.grupo8.service.PrioridadRequerimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="/prioridad_requerimiento")
public class PrioridadRequerimientoController {
    @Autowired
    private PrioridadRequerimientoService prioridadRequerimientoService;

    @GetMapping
    public List<PrioridadRequerimiento> getPrioridadRequerimiento() {
        return prioridadRequerimientoService.getPrioridadRequerimiento();
    }
}
