package com.ar.grupo8.controllers;

import com.ar.grupo8.models.Cargo;
import com.ar.grupo8.service.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="/cargos")
public class CargoController {
    @Autowired
    private CargoService cargoService;

    @GetMapping
    public List<Cargo> getCargos() {
        return cargoService.getCargos();
    }
}
