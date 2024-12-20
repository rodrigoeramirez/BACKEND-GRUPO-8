package com.ar.grupo8.service;

import com.ar.grupo8.models.Departamento;
import com.ar.grupo8.repository.DepartamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartamentoService {


    @Autowired
    DepartamentoRepository departamentoRepository;

    public List<Departamento> getDepartamentos() {
        return departamentoRepository.findAll();
    }
}
