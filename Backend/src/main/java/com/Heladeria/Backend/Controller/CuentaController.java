package com.Heladeria.Backend.Controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.Heladeria.Backend.DTO.ClienteRequest;
import com.Heladeria.Backend.DTO.ClienteResponse;
import com.Heladeria.Backend.Service.ClienteService;

import jakarta.validation.Valid;

/**
 * Controlador que maneja las rutas de autenticación y registro.
 * Estas rutas suelen ser públicas (no requieren token).
 */
@RestController
@RequestMapping("/api/auth")
public class CuentaController {

    private final ClienteService clienteService;

    @Autowired
    public CuentaController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // --- Endpoint para el Registro de Clientes ---
    // Mapea la ruta /api/auth/register-cliente
    
    @PostMapping("/register-cliente")
    public ResponseEntity<ClienteResponse> registerCliente(@Valid @RequestBody ClienteRequest registroDTO) {
        
        // El ClienteService se encarga de crear la Cuenta y cifrar la contraseña
        ClienteResponse clienteResponse = clienteService.registrarCliente(registroDTO);
        
        // 201 Created: Devuelve la ubicación del nuevo recurso creado
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(clienteResponse.getId())
                .toUri();
                
        return ResponseEntity.created(location).body(clienteResponse);
    }
    
    // NOTA: Los endpoints de LOGIN (POST /api/auth/login) se manejarán automáticamente
    // por la configuración de Spring Security (JWT), por lo que no requieren un método aquí.
    
    // NOTA 2: Se puede añadir un endpoint para el registro de empleados si se desea, 
    // pero idealmente, solo los ADMINS deberían poder crear empleados.
}