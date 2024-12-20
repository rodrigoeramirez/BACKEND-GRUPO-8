package com.ar.grupo8.service;

import com.ar.grupo8.models.Cargo;
import com.ar.grupo8.repository.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CargoService {
    @Autowired
    private CargoRepository cargoRepository;

    public List<Cargo> getCargos() {
        return cargoRepository.findAll();
    }
}
