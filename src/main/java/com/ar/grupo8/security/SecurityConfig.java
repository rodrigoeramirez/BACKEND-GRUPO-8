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
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.firewall.DefaultHttpFirewall;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig implements WebMvcConfigurer {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public HttpFirewall relaxedHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true);  // ✅ Permitir barras en URLs
        firewall.setAllowUrlEncodedPercent(true); // ✅ Permitir % en URLs
        firewall.setAllowSemicolon(true); // ✅ Permitir punto y coma
        firewall.setAllowBackSlash(true); // ✅ Permitir backslash "\"
        firewall.setAllowUrlEncodedPeriod(true); // ✅ Permitir puntos en URLs
        firewall.setAllowUrlEncodedDoubleSlash(true); // ✅ Permitir doble slash
        return firewall;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // ✅ Configurar CORS
                .csrf(config -> config.disable()) // Desactiva CSRF
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/auth/**", "/departamentos", "/cargos","/archivos_adjuntos/**","/archivos_adjuntos*", "/usuarios/validate-username/**", "/usuarios/validate-email/**").permitAll(); // Rutas sin autenticación
                    auth.anyRequest().authenticated(); // El resto requiere autenticación
                })
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS); // No usar sesiones
                })
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Usa la configuración de CORS
                .build();
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // CORS Configuration
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:5173");  // Permite solicitudes desde este origen
        config.addAllowedMethod("GET"); // Permite solicitudes GET
        config.addAllowedMethod("POST"); // Permite solicitudes POST
        config.addAllowedMethod("PUT"); // Permite solicitudes PUT
        config.addAllowedMethod("PATCH"); // Permite solicitudes PATCH
        config.addAllowedMethod("DELETE"); // Permite solicitudes DELETE
        config.addAllowedHeader("Authorization"); // Permite el encabezado Authorization
        config.addAllowedHeader("Content-Type"); // Permite el encabezado Content-Type
        config.setAllowCredentials(true);  // Permite credenciales (cookies, tokens)

        // Aplica la configuración CORS para todas las rutas
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}


