package com.Heladeria.Backend.Controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; // Importación estandarizada
import org.springframework.http.ResponseEntity; // Necesario para la validación
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.Heladeria.Backend.DTO.EmpleadoRequest;
import com.Heladeria.Backend.DTO.EmpleadoResponse;
import com.Heladeria.Backend.Service.EmpleadoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {
    
    private final EmpleadoService empleadoService;

    @Autowired
    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    // --- 1. REGISTRO (POST) ---
    // Endpoint para crear nuevos empleados (Debe estar protegido con Rol ADMIN)
    @PostMapping("/registro")
    public ResponseEntity<EmpleadoResponse> registrarEmpleado(@Valid @RequestBody EmpleadoRequest registroDTO) {
        
        // CORRECCIÓN: Pasar el DTO completo
        EmpleadoResponse responseDTO = empleadoService.registrarEmpleado(registroDTO);
        
        // Devolver 201 Created y la URI del nuevo recurso
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDTO.getId())
                .toUri();
                
        return ResponseEntity.created(location).body(responseDTO);
    }

    // --- 2. OBTENER POR ID (GET) ---
    // Devolverá los datos no sensibles del empleado
    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoResponse> obtenerEmpleadoPorId(@PathVariable Long id) {
        // Asumiendo que el método en el Service es obtenerEmpleadoPorId (consistencia)
        EmpleadoResponse responseDTO = empleadoService.obtenerEmpleadoPorId(id); 
        return ResponseEntity.ok(responseDTO);
    }

    // --- 3. OBTENER TODOS (GET) ---
    // Devolver lista de todos los empleados (Solo para Rol ADMIN)
    @GetMapping
    public ResponseEntity<List<EmpleadoResponse>> obtenerTodosLosEmpleados() {
        List<EmpleadoResponse> responseDTOs = empleadoService.obtenerTodosEmpleados(); // Nombre estandarizado
        return ResponseEntity.ok(responseDTOs);
    }
}