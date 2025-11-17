package com.Heladeria.Backend.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod; // <-- IMPORTACIÓN NECESARIA
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer; // <-- IMPORTACIÓN NECESARIA
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityBeansConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Desactivar CSRF (necesario para APIs REST)
            .csrf(AbstractHttpConfigurer::disable)
            
            // 2. Configurar las reglas de autorización
            .authorizeHttpRequests(authorize -> authorize
                // Permitir acceso público a la creación de clientes (Registro)
                .requestMatchers(HttpMethod.POST, "/api/clientes").permitAll() // ¡Ruta Pública!
                
                // Permitir acceso público al login
                .requestMatchers("/api/auth/**").permitAll() // Asumiendo que el login está aquí
                
                // C-2: Leer Lista de Clientes (GET) debe ser público para esta prueba.
    .requestMatchers(HttpMethod.GET, "/api/clientes").permitAll() // <--- ¡Asegúrate de que esta línea esté!
    
           // ... agrega las rutas de empleados, productos y pedidos aquí:
         .requestMatchers(HttpMethod.POST, "/api/empleados").permitAll()
          .requestMatchers(HttpMethod.GET, "/api/empleados").permitAll()
          .requestMatchers(HttpMethod.POST, "/api/productos").permitAll()
          .requestMatchers(HttpMethod.GET, "/api/productos").permitAll()
         .requestMatchers(HttpMethod.POST, "/api/pedidos").permitAll()
         .requestMatchers(HttpMethod.GET, "/api/pedidos/**").permitAll()
                // Requerir autenticación para cualquier otra petición
                .anyRequest().authenticated()
            );

        return http.build();
    }
}