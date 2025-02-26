package com.ar.grupo8.jwt;

import com.ar.grupo8.models.UsuarioEmpresa;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret.key}")
    private String key;

    @Value("${jwt.expiration}")
    private long expiration;

    // Convierte la clave secreta de String a SecretKey
    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(key.getBytes());
    }

    public String getEmailFromToken(String token) {
        return getClaim(token, claims -> claims.get("email", String.class));
    }

    public String getNombreFromToken(String token) {
        return getClaim(token, claims -> claims.get("nombre", String.class));
    }

    public String getLegajoFromToken(String token) {
        return getClaim(token, claims -> claims.get("legajo", String.class));
    }

    public String getApellidoFromToken(String token) {
        return getClaim(token, claims -> claims.get("apellido", String.class));
    }

    public String getToken(UsuarioEmpresa usuarioEmpresa) {
        return getToken(new HashMap<>(), usuarioEmpresa);
    }

    private String getToken(Map<String, Object> extraClaims, UsuarioEmpresa usuarioEmpresa) {
        extraClaims.put("email", usuarioEmpresa.getEmail());
        extraClaims.put("nombre", usuarioEmpresa.getNombre());
        extraClaims.put("apellido", usuarioEmpresa.getApellido());
        extraClaims.put("legajo", usuarioEmpresa.getLegajo());
        extraClaims.put("iniciales", usuarioEmpresa.getIniciales()); // Agrega las iniciales

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(usuarioEmpresa.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }




    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username =getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Claims getAllClaims (String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T getClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpiration(String token){
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired (String token) {
        return getExpiration(token).before(new Date());
    }
}

