package com.Heladeria.Backend.Controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity; // Importar la anotación para validación
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable; // Importar HttpStatus
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.Heladeria.Backend.DTO.ClienteRequest;
import com.Heladeria.Backend.DTO.ClienteResponse;
import com.Heladeria.Backend.Service.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    
    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }
    
    // --- 1. REGISTRO (POST) ---
    // Devolvemos 201 Created y la URL del nuevo recurso
    @PostMapping("/registro")
    public ResponseEntity<ClienteResponse> registrarCliente(@Valid @RequestBody ClienteRequest clienteRegistroDTO) {
        // @Valid es CRUCIAL para que se disparen las validaciones del DTO
        
        ClienteResponse clienteResponse = clienteService.registrarCliente(clienteRegistroDTO);
        
        // Generar la URI del recurso creado (Mejor práctica HTTP: 201 Created)
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(clienteResponse.getId())
                .toUri();
                
        return ResponseEntity.created(location).body(clienteResponse);
    }
    
    // --- 2. OBTENER POR ID (GET) ---
    // Nota: El manejo de la excepción 404 debe hacerse en un @ControllerAdvice
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> obtenerClientePorId(@PathVariable Long id) {
        ClienteResponse clienteResponse = clienteService.obtenerClientePorId(id); // Nombre de método estandarizado
        return ResponseEntity.ok(clienteResponse);
    }
    
    // --- 3. OBTENER TODOS (GET) ---
    // Útil para administración, probablemente necesite seguridad (Rol ADMIN)
    @GetMapping
    public ResponseEntity<List<ClienteResponse>> obtenerTodosClientes() {
        List<ClienteResponse> clientes = clienteService.obtenerTodosClientes();
        return ResponseEntity.ok(clientes);
    }
}
