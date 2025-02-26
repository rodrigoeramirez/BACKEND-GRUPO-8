package com.ar.grupo8.service;

import com.ar.grupo8.models.EstadoRequerimiento;
import com.ar.grupo8.repository.EstadoRequerimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoRequerimientoService {
    @Autowired
    private EstadoRequerimientoRepository estadoRequerimientoRepository;

    public List<EstadoRequerimiento> getEstadoRequerimiento(){
        return estadoRequerimientoRepository.findAll();
    }
}
