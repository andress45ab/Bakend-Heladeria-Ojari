package com.Heladeria.Backend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping; // Importar la anotación para validación
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping; // Importar HttpStatus
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Heladeria.Backend.DTO.ClienteResponse;
import com.Heladeria.Backend.Service.ClienteService;
import com.Heladeria.Backend.model.Cliente;

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
   @PostMapping // El @RequestMapping base es "/api/clientes". Esta es la ruta POST /api/clientes
    public ResponseEntity<Cliente> crearCliente(@Valid @RequestBody Cliente cliente) {
        // Lógica para guardar el cliente y hashear la contraseña
        Cliente nuevoCliente = clienteService.save(cliente); 
        
        // Devolver 201 Created
        return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);
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
