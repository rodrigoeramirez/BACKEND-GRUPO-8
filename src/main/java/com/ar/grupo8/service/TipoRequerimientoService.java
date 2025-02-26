package com.ar.grupo8.service;

import com.ar.grupo8.models.Requerimiento;
import com.ar.grupo8.models.TipoRequerimiento;
import com.ar.grupo8.repository.TipoRequerimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoRequerimientoService {

    @Autowired
    private TipoRequerimientoRepository tipoRequerimientoRepository;

    public List<TipoRequerimiento> getTipoRequerimiento () {
        return tipoRequerimientoRepository.findAll();
    }
}
