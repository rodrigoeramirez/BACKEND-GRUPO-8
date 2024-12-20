package com.ar.grupo8.service;
import com.ar.grupo8.auth.AuthResponse;
import com.ar.grupo8.dto.UpdateUsuarioEmpresaDto;
import com.ar.grupo8.dto.UsuarioEmpresaDto;
import com.ar.grupo8.jwt.JwtService;
import com.ar.grupo8.models.Cargo;
import com.ar.grupo8.models.Departamento;
import com.ar.grupo8.models.UsuarioEmpresa;
import com.ar.grupo8.repository.UsuarioEmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioEmpresaService {
    // Autowired le dice a Spring que debe inyectar automáticamente una dependencia (en este caso, UsuarioEmpresaRepository)
    // En lugar de crear manualmente una instancia del repositorio (por ejemplo, new UsuarioEmpresaRepository()), Spring se encarga de gestionarlo.
    @Autowired
    UsuarioEmpresaRepository usuarioEmpresaRepository; // Defino el repositorio.

    @Autowired
    JwtService jwtService; // Se encarga de crear el token cuando el usuario se crea con exito.

    private final PasswordEncoder passwordEncoder;

    public boolean isEmailAvailable(String email) {
        return usuarioEmpresaRepository.findByEmail(email).isEmpty();
    }

    public boolean isUserNameAvailable(String username) {
        return usuarioEmpresaRepository.findByUsername(username).isEmpty();
    }

    public boolean isLegajoAvailable(Integer legajo) {
        return usuarioEmpresaRepository.findByLegajo(legajo).isEmpty();
    }

    public List<UsuarioEmpresaDto> getUsuariosEmpresa () {
        List<UsuarioEmpresa> usuarios = usuarioEmpresaRepository.findAllByActivoTrue();
        // Convierto la lista de ususarios en un stream.
        // Un stream es una secuencia de elementos que te permite realizar operaciones funcionales como mapear, filtrar o transformar datos.
        // Aquí se usa para iterar sobre cada objeto UsuarioEmpresa de la lista.
        return usuarios.stream()
                // La operación map aplica una función a cada elemento del stream y transforma esos elementos en algo diferente.
                // Cada objeto UsuarioEmpresa es transformado en un nuevo objeto UsuarioEmpresaDTO.
                .map(usuario -> new UsuarioEmpresaDto(
                        usuario.getNombre(),
                        usuario.getApellido(),
                        usuario.getUsername(),
                        usuario.getEmail(),
                        usuario.getLegajo(),
                        usuario.getCargo().getNombre(),
                        usuario.getDepartamento().getNombre()
                ))
                .toList(); //Después de transformar los elementos con map, el stream resultante es convertido nuevamente a una lista con el metodo toList().
        //Tipo de dato final: List<UsuarioEmpresaDTO>.
    }

    public Optional<UsuarioEmpresaDto> getUsuarioEmpresaById (Integer legajo) {
        // Busca al usuario por su Legajo en la base de datos.
        // Devuelve un Optional<UsuarioEmpresa>.
        return usuarioEmpresaRepository.findByLegajo(legajo)
                // Si el Optional no está vacío, transforma el UsuarioEmpresa en un UsuarioEmpresaDto.
                .map(usuario -> new UsuarioEmpresaDto(
                        usuario.getNombre(),                   // Obtiene el nombre.
                        usuario.getApellido(),                 // Obtiene el apellido.
                        usuario.getUsername(),                 // Obtiene el username.
                        usuario.getEmail(),                    // Obtiene el email.
                        usuario.getLegajo(),                   // Obtiene el legajo.
                        usuario.getCargo().getNombre(),            // Obtiene el nombre del cargo.
                        usuario.getDepartamento().getNombre()      // Obtiene el nombre del departamento.
                ));
    }

    public AuthResponse createUsuarioEmpresa (UsuarioEmpresaDto usuarioEmpresaDTO) {
        UsuarioEmpresa usuarioEmpresa = new UsuarioEmpresa();

        usuarioEmpresa.setNombre(usuarioEmpresaDTO.getNombre());
        usuarioEmpresa.setApellido(usuarioEmpresaDTO.getApellido());
        // Encriptar la clave antes de guardarla
        String encryptedPassword = passwordEncoder.encode(usuarioEmpresaDTO.getClave());
        usuarioEmpresa.setClave(encryptedPassword); // Usamos la clave encriptada

        if (isUserNameAvailable(usuarioEmpresaDTO.getUsername())) {
            usuarioEmpresa.setUsername(usuarioEmpresaDTO.getUsername());
        } else {
            throw new RuntimeException("El username ya está registrado");
        }

        if (isEmailAvailable(usuarioEmpresaDTO.getEmail())) {
            usuarioEmpresa.setEmail(usuarioEmpresaDTO.getEmail());
        } else {
            throw new RuntimeException("El email ya está registrado");
        }

        // En usuarioEmpresaDTO me llegan los id para la foranea, sin embargo, en el servicio (UsuarioEmpresaService), necesito mapear estos IDs a los objetos Departamento y Cargo correspondientes.
        // Lo que realmente está haciendo este fragmento de código es configurar una relación sin persistir un nuevo objeto Departamento en la base de datos.
        Departamento departamento = new Departamento(); // Se está creando un objeto en memoria para asociarlo al UsuarioEmpresa (NO SERÁ PERSISTIDO).
        departamento.setId(usuarioEmpresaDTO.getDepartamento_id()); // Se asigna el departamento_id proporcionado en el DTO al objeto Departamento recién creado.
        usuarioEmpresa.setDepartamento(departamento); // Esto es suficiente para que JPA (Hibernate) entienda que este Departamento ya existe en la base de datos. Por lo tanto solo resolvera la relacion.

        Cargo cargo = new Cargo();
        cargo.setId(usuarioEmpresaDTO.getCargo_id());
        usuarioEmpresa.setCargo(cargo);

        // Guardar el objeto UsuarioEmpresa en la base de datos
        usuarioEmpresaRepository.save(usuarioEmpresa);

        return  AuthResponse.builder()
                .token(jwtService.getToken(usuarioEmpresa))
                .build();
    }

    public void updateUsuarioEmpresa(Integer legajo, UpdateUsuarioEmpresaDto usuarioEmpresaDto) {
        // Buscar el usuario por legajo
        UsuarioEmpresa usuario = usuarioEmpresaRepository.findByLegajo(legajo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Actualizar los campos del usuario
        if (usuarioEmpresaDto.getNombre() != null){
            usuario.setNombre(usuarioEmpresaDto.getNombre());
        }
        if (usuarioEmpresaDto.getApellido() != null){
            usuario.setApellido(usuarioEmpresaDto.getApellido());
        }
        if (usuarioEmpresaDto.getUsername() != null) {
            if (isUserNameAvailable(usuarioEmpresaDto.getUsername())) {
                usuario.setUsername(usuarioEmpresaDto.getUsername());
            } else {
                throw new RuntimeException("El username ya está registrado");
            }
        }
        if (usuarioEmpresaDto.getClave() != null){
            // Encriptar la clave antes de guardarla
            String encryptedPassword = passwordEncoder.encode(usuarioEmpresaDto.getClave());
            usuario.setClave(encryptedPassword); // Usamos la clave encriptada
        }
        if (usuarioEmpresaDto.getEmail() != null){
            if (isEmailAvailable(usuarioEmpresaDto.getEmail())) {
                usuario.setEmail(usuarioEmpresaDto.getEmail());
            } else {
                throw new RuntimeException("El email ya está registrado");
            }
        }

        // Asignar el id del departamento y cargo directamente
        if (usuarioEmpresaDto.getDepartamentoId() != null){
            Departamento departamento = new Departamento();
            departamento.setId(usuarioEmpresaDto.getDepartamentoId()); // Asignar el id al departamento
            usuario.setDepartamento(departamento); // Establecer la relacion
        }
        if (usuarioEmpresaDto.getCargoId() != null){
            Cargo cargo = new Cargo();
            cargo.setId(usuarioEmpresaDto.getCargoId()); // Asignar el id al cargo
            usuario.setCargo(cargo); // Establecer la relacion
        }
        // Guardar el usuario actualizado
        usuarioEmpresaRepository.save(usuario);
    }

    public void deleteUsuarioEmpresa (Integer legajo) {
        // Busca el usuario por su ID, lanza una excepción si no existe
        UsuarioEmpresa usuario = usuarioEmpresaRepository.findByLegajo(legajo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Cambia el estado a inactivo
        usuario.setActivo(false);

        // Guarda el registro actualizado
        usuarioEmpresaRepository.save(usuario);
    }
}
