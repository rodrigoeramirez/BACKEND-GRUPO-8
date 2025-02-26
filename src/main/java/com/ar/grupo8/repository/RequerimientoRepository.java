package com.ar.grupo8.repository;

import com.ar.grupo8.models.Requerimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface RequerimientoRepository extends JpaRepository<Requerimiento,Long> {
    List<Requerimiento> findAllByActivoTrue();
    // Metodo para consultar el último código generado. Utiliza un query basado en el orden descendente del ID o del campo codigo.
    @Query("SELECT COALESCE(MAX(r.id), 0) FROM Requerimiento r WHERE r.tipoRequerimiento.id = :tipoRequerimientoId")
    Integer findUltimoSecuencial(@Param("tipoRequerimientoId") Long tipoRequerimientoId);

    Optional<Requerimiento> findByCodigo(String codigo);
}
