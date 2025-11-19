package com.Heladeria.Backend.Controller;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager; // Tu CuentaResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; // Tu LoginDTO
import org.springframework.security.core.Authentication; // Tu proveedor de JWT
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping; // ¡Necesitas esta!
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder; // 💥 ¡NECESARIO!

import com.Heladeria.Backend.DTO.ClienteRequest; // 💥 ¡NECESARIO!
import com.Heladeria.Backend.DTO.ClienteResponse;
import com.Heladeria.Backend.DTO.CuentaResponse;
import com.Heladeria.Backend.DTO.LoginDTO;
import com.Heladeria.Backend.Security.JwtTokenProvider;
import com.Heladeria.Backend.Service.ClienteService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/auth")
public class CuentaController { // O AuthController, como prefieras

    private final ClienteService clienteService;
    private final AuthenticationManager authenticationManager; // Nueva
    private final JwtTokenProvider tokenProvider;             // Nueva

    // 1. Constructor con todas las inyecciones
    public CuentaController(ClienteService clienteService,
                            AuthenticationManager authenticationManager,
                            JwtTokenProvider tokenProvider) {
        this.clienteService = clienteService;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    // --- Endpoint para el Registro de Clientes (EXISTENTE) ---
    @PostMapping("/register")
    public ResponseEntity<ClienteResponse> registerCliente(@Valid @RequestBody ClienteRequest registroDTO) {
        // ... (Tu lógica existente de registro)
        ClienteResponse clienteResponse = clienteService.registrarCliente(registroDTO);
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(clienteResponse.getId())
                .toUri();
                
        return ResponseEntity.created(location).body(clienteResponse);
    }
    
   // --- 2. Endpoint para el Login ---
// Mapea la ruta POST /api/auth/login
@PostMapping("/login")
public ResponseEntity<CuentaResponse> login(@RequestBody LoginDTO loginDto) {
    
    // 1. Autenticar las credenciales
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginDto.getUsuario(),
            loginDto.getContrasena())
    );
    
    // 2. Establecer el contexto de seguridad (opcional)
    SecurityContextHolder.getContext().setAuthentication(authentication);

    // 3. Generar el Token
    String token = tokenProvider.generateToken(authentication);

    // 💥 4. OBTENER Y FORMATEAR EL ROL 💥
    // Mapea las autoridades (roles) del usuario y elimina el prefijo "ROLE_".
    String rol = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority) // Mapea a String (ej: "ROLE_ADMIN")
                .findFirst()
                .map(s -> s.replace("ROLE_", "")) // Elimina el prefijo para enviar solo "ADMIN"
                .orElse("CLIENTE"); // Valor por defecto si no se encuentra rol

    // 5. Devolver el token y el rol
    // ¡Usando el nuevo constructor CuentaResponse(token, rol)!
    return new ResponseEntity<>(new CuentaResponse(token, rol), HttpStatus.OK); 
}
}