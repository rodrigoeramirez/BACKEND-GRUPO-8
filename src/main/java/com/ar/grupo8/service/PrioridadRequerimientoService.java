package com.ar.grupo8.service;

import com.ar.grupo8.models.PrioridadRequerimiento;
import com.ar.grupo8.repository.PrioridadRequerimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrioridadRequerimientoService {
    @Autowired
    private PrioridadRequerimientoRepository prioridadRequerimientoRepository;

    public List<PrioridadRequerimiento> getPrioridadRequerimiento(){
        return prioridadRequerimientoRepository.findAll();
    }
}
