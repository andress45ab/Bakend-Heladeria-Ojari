// En com.Heladeria.Backend.Config.SecurityBeansConfig.java

package com.Heladeria.Backend.Config;

import com.Heladeria.Backend.Security.JwtAuthenticationFilter; // ¡Importar tu filtro JWT!
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; // Para añadir tu filtro
import org.springframework.web.cors.CorsConfiguration; // Para CORS
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) 
public class SecurityBeansConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // Inyección de tu filtro JWT
    public SecurityBeansConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    // --- Beans Esenciales ---
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    // Este Bean es crucial para el Login en el AuthController
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    
    // --- Configuración Final de Seguridad (JWT, Roles y CORS) ---

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Configuración de CORS
            .cors(cors -> cors.configurationSource(request -> {
                CorsConfiguration config = new CorsConfiguration();
                // Permite tu frontend o localhost de desarrollo
                config.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://127.0.0.1:5500")); 
                config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                config.setAllowedHeaders(Arrays.asList("*"));
                config.setAllowCredentials(true);
                return config;
            }))
            
            // 2. Deshabilitar CSRF
            .csrf(csrf -> csrf.disable())
            
            // 3. Configurar como sin estado (STATELESS)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // 4. Configurar las Autorizaciones de Rutas
            .authorizeHttpRequests(authorize -> authorize
                // Rutas Públicas (Registro y Login)
                .requestMatchers("/api/auth/**").permitAll()                     
                .requestMatchers(HttpMethod.POST, "/api/clientes").permitAll() // Registro de Cliente
                
                // --- Rutas Protegidas por Roles ---
                
                // CRUD de Clientes (Solo Admin puede ver todos, el Cliente solo puede ver el suyo)
                .requestMatchers(HttpMethod.GET, "/api/clientes").hasAnyRole("ADMIN", "EMPLEADO") 
                .requestMatchers(HttpMethod.GET, "/api/clientes/{id}").authenticated() // Cualquier usuario autenticado puede ver su perfil
                
                // CRUD de Empleados (Solo ADMIN)
                .requestMatchers("/api/empleados/**").hasRole("ADMIN")

                // CRUD de Productos (Admin y Empleado pueden manejar inventario)
                .requestMatchers("/api/productos/**").hasAnyRole("ADMIN", "EMPLEADO")
                
                // CRUD de Pedidos (Todos pueden hacer/ver sus pedidos)
                .requestMatchers("/api/pedidos/**").authenticated() 
                
                // Cualquier otra petición (si no coincide con nada de lo anterior) requiere autenticación
                .anyRequest().authenticated() 
            );

        // 5. Añadir el Filtro JWT
        // Es crucial que tu filtro JWT se ejecute antes del filtro estándar de autenticación de Spring
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}