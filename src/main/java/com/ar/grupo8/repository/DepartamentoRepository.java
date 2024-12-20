package com.ar.grupo8.repository;

import com.ar.grupo8.models.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;

// Estos repositorios se encargan de interactuar con la base de datos.
// Se utiliza Spring Data JPA, que ofrece métodos predeterminados para las operaciones CRUD.
public interface DepartamentoRepository extends JpaRepository<Departamento,Long> {
}
