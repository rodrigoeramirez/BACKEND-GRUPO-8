package com.ar.grupo8.auth;

import com.ar.grupo8.models.UsuarioEmpresa;
import com.ar.grupo8.repository.UsuarioEmpresaRepository;
import com.ar.grupo8.jwt.JwtService;
import com.ar.grupo8.service.UsuarioEmpresaDetailsService;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UsuarioEmpresaRepository usuarioEmpresaRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioEmpresaDetailsService usuarioEmpresaDetailsService;


    public AuthResponse login(LoginRequest request) {
        try {
            // Intenta autenticar al usuario
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getClave())
            );

            // Si la autenticación es exitosa, genera el token
            UserDetails userDetails = usuarioEmpresaDetailsService.loadUserByUsername(request.getUsername());
            String token = jwtService.getToken((UsuarioEmpresa) userDetails);

            return AuthResponse.builder()
                    .token(token)
                    .build();

        } catch (BadCredentialsException e) {
            // Si la autenticación falla por usuario o clave incorrectos
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario o clave incorrectos");
        } catch (Exception e) {
            // Si ocurre algún otro error
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error en el servidor: " + e.getMessage());
        }
    }

}
