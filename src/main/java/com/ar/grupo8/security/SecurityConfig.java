package com.ar.grupo8.security;

import com.ar.grupo8.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Dentro de la clase creo un metodo que configure la seguridad.
@Configuration
@RequiredArgsConstructor// Esta anotación le dice a Spring que es una clase de tipo configuración.
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    // Esta anotación se utiliza dentro de una clase configurada como @Configuration.
    @Bean  // //Indica que el metodo securityFilterChain devuelve un objeto que Spring debe gestionar y usar como parte de la aplicación.
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // SecurityFilterChain: Es el tipo del objeto que se va a crear y registrar. En este caso, es un objeto que configura la seguridad de la aplicación.
        return http // con este httpSecurity se configura la seguridad de toda la aplicación.
                .csrf(config -> config.disable()) // CSRF es un tipo de ataque donde un atacante engaña a un usuario autenticado para que realice acciones no deseadas en un sistema. Como voy a usar JWT la protección CSRF no es necesaria y puede ser desactivada.
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/auth/**").permitAll(); // Esta ruta permite ser accedida sin autenticación.
                    auth.anyRequest().authenticated(); // Cualquier otro endpoint el usuario debe estar autenticado.
                })
                    .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Configura la política de manejo de sesiones en Spring Security, estableciendo que la aplicación no utilizará sesiones para gestionar la autenticación del usuario.
                })
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
