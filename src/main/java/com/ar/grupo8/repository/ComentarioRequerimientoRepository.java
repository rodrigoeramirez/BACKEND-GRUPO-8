package com.ar.grupo8.repository;

import com.ar.grupo8.models.ComentarioRequerimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComentarioRequerimientoRepository extends JpaRepository<ComentarioRequerimiento,Long> {
    List<ComentarioRequerimiento> findByRequerimientoId(Long requerimiento_id);
}
