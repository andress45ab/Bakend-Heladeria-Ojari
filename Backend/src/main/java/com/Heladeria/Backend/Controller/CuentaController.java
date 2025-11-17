package com.Heladeria.Backend.Controller;

import com.Heladeria.Backend.DTO.ClienteRequest;
import com.Heladeria.Backend.DTO.ClienteResponse;
import com.Heladeria.Backend.Service.ClienteService;
import com.Heladeria.Backend.DTO.CuentaResponse; // Tu CuentaResponse
import com.Heladeria.Backend.DTO.LoginDTO; // Tu LoginDTO
import com.Heladeria.Backend.Security.JwtTokenProvider; // Tu proveedor de JWT

import jakarta.validation.Valid;
import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager; // ¡Necesitas esta!
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


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
    @PostMapping("/register-cliente")
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
    
    // --- 2. Endpoint para el Login (NUEVO) ---
    // Mapea la ruta POST /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<CuentaResponse> login(@RequestBody LoginDTO loginDto) {
        
        // 1. Autenticar las credenciales
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsuario(),
                        loginDto.getContrasena())
        );
        
        // 2. Establecer el contexto de seguridad (opcional, pero buena práctica)
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. Generar el Token
        String token = tokenProvider.generateToken(authentication);

        // 4. Devolver el token
        // NOTA: Si usaste CuentaResponse en lugar de AuthResponseDto, ajusta el tipo de retorno aquí.
        return new ResponseEntity<>(new CuentaResponse( token), HttpStatus.OK); 
    }
}