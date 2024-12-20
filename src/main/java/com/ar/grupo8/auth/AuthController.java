package com.ar.grupo8.auth;


import com.ar.grupo8.dto.UsuarioEmpresaDto;
import com.ar.grupo8.service.UsuarioEmpresaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UsuarioEmpresaService usuarioEmpresaService;
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request){
        // Uso ResponseEntity para personalizar la respuesta, pero tengo que devolver el token en realidad.
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody UsuarioEmpresaDto request){
        return ResponseEntity.ok(usuarioEmpresaService.createUsuarioEmpresa(request));
    }
}
