package com.ar.grupo8.controllers;

import com.ar.grupo8.models.TipoRequerimiento;
import com.ar.grupo8.service.TipoRequerimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="/tipo_requerimiento")
public class TipoRequerimientoController {
    @Autowired
    private TipoRequerimientoService tipoRequerimientoService;

    @GetMapping
    public List<TipoRequerimiento> getTipoRequerimiento(){
        return tipoRequerimientoService.getTipoRequerimiento();
    }
}
