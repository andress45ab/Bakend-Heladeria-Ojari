package com.Heladeria.Backend.Security;

import com.Heladeria.Backend.model.Cuenta;
import com.Heladeria.Backend.Repository.CuentaRepository;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;  
import java.util.List;

@Service
public class CustomUserDetails implements UserDetailsService {
    private final CuentaRepository cuentaRepository;

    public CustomUserDetails(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }
//Metodo Principal para Cargar el Usuario
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Buscar la cuenta por usuario (email)
        Cuenta cuenta = cuentaRepository.findByUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el email: " + username));

        // 2. Mapear el rol (ej. CLIENTE) a una Autoridad de Spring Security (ej. ROLE_CLIENTE)
        List<GrantedAuthority> authorities = Collections.singletonList(
            new SimpleGrantedAuthority("ROLE_" + cuenta.getRol().name()) 
            // NOTA: Spring Security requiere el prefijo "ROLE_"
        );
        
        // 3. Devolver el objeto UserDetails con las credenciales y roles
        return new User(
                cuenta.getUsuario(),    // Nombre de usuario
                cuenta.getContrasena(), // Contraseña (el hash)
                authorities             // Los roles
        );
    }
}


