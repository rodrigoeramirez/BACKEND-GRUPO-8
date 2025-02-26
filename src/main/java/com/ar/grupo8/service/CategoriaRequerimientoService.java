package com.ar.grupo8.service;

import com.ar.grupo8.models.CategoriaRequerimiento;
import com.ar.grupo8.repository.CategoriaRequerimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaRequerimientoService {

    @Autowired
    private CategoriaRequerimientoRepository categoriaRequerimientoRepository;

    public List<CategoriaRequerimiento> getCategoriaRequerimiento() {
        return categoriaRequerimientoRepository.findAll();
    }
}
