package com.ar.grupo8.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
@Entity
@Table(name="usuario_empresa")
public class UsuarioEmpresa implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "legajo", unique = true, nullable = false)
    private Integer legajo;  // 'legajo' es ahora la clave primaria autoincremental

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "clave", nullable = false)
    private String clave;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    @ManyToOne
    @JoinColumn(name = "departamento_id", nullable = false)
    private Departamento departamento;

    @ManyToOne
    @JoinColumn(name = "cargo_id", nullable = false)
    private Cargo cargo;

    // Métodos requeridos por UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Si deseas manejar roles, puedes retornar una lista de roles desde aquí.
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return clave; // Retorna la clave del usuario
    }

    @Override
    public String getUsername() {
        return username; // Retorna el nombre de usuario
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Define si la cuenta está expirada
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Define si la cuenta está bloqueada
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Define si las credenciales están expiradas
    }

    @Override
    public boolean isEnabled() {
        return activo; // Define si el usuario está activo
    }
}

