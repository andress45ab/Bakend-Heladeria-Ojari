// En com.Heladeria.Backend.config.DataLoader.java (Versión Factorizada y Genérica)

package com.Heladeria.Backend.Config;

import java.util.List;
 
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.Heladeria.Backend.Repository.CuentaRepository;
import com.Heladeria.Backend.model.Cuenta;
import com.Heladeria.Backend.model.Rol;

@Configuration
public class DataLoader {

    // (Opcional) Clase interna o record para definir los datos de prueba
    // Si usas Java 16+, puedes usar record. Si usas versiones anteriores, usa una clase simple.
    private static class TestUser {
        String usuario;
        String contrasena;
        Rol rol;

        public TestUser(String usuario, String contrasena, Rol rol) {
            this.usuario = usuario;
            this.contrasena = contrasena;
            this.rol = rol;
        }
    }

    @Bean
    public CommandLineRunner initData(CuentaRepository cuentaRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            
            // 1. Definir la lista de usuarios de prueba (Datos Maestros)
            List<TestUser> testUsers = List.of(
                new TestUser("admin@heladeria.com", "admin123", Rol.ADMIN),
                new TestUser("empleado@heladeria.com", "empleado123", Rol.EMPLEADO),
                new TestUser("cliente_test@heladeria.com", "cliente123", Rol.CLIENTE)
                // Si agregas más roles, solo añades una línea aquí.
            );
            
            // 2. Iterar y Verificar/Crear (Lógica Factorizada)
            testUsers.forEach(testUser -> {
                // Verificar si el usuario de prueba NO existe
                if (cuentaRepository.findByUsuario(testUser.usuario).isEmpty()) {
                    
                    Cuenta cuenta = new Cuenta();
                    cuenta.setUsuario(testUser.usuario);
                    cuenta.setContrasena(passwordEncoder.encode(testUser.contrasena)); 
                    cuenta.setRol(testUser.rol);
                    cuentaRepository.save(cuenta);
                    
                    System.out.println("✅ Cuenta de prueba " + testUser.rol + " creada: " + testUser.usuario);
                }
            });
        };
    }
}
