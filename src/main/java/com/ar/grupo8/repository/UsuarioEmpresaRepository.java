package com.ar.grupo8.repository;
import com.ar.grupo8.models.UsuarioEmpresa;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

// Estos repositorios se encargan de interactuar con la base de datos.
// Se utiliza Spring Data JPA, que ofrece métodos predeterminados para las operaciones CRUD.

public interface UsuarioEmpresaRepository extends JpaRepository<UsuarioEmpresa,Long> {
    // Como estoy usando Spring Data JPA, no necesito implementar el metodo en el repositorio si sigo la naming convention de JPA.
    // Cuando defino un metodo como findAllByActivoTrue(), Spring Data JPA genera automáticamente la consulta basada en el nombre del metodo.
    List<UsuarioEmpresa> findAllByActivoTrue(); // Implementación automatica: Busca (findAll) todas las entidades donde el campo activo sea true.
    Optional<UsuarioEmpresa> findByEmail(String email); // Creo el metodo de consulta personalizado simplemente siguiendo una nomenclatura específica en tu interfaz de repositorio.
    Optional<UsuarioEmpresa> findByUsername(String username);
    Optional<UsuarioEmpresa> findByLegajo(Integer legajo);
}
