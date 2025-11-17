package com.Heladeria.Backend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping; // Usamos ResponseEntity
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Heladeria.Backend.DTO.ProductoDTO;
import com.Heladeria.Backend.Service.ProductoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    
    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }
    
    // --- 1. GET TODOS LOS PRODUCTOS (Menú) ---
    // Seguridad: Accesible por todos (CLIENTE, EMPLEADO, ADMIN, Anónimo)
    @GetMapping
    public ResponseEntity<List<ProductoDTO>> listarTodos() {
        List<ProductoDTO> productos = productoService.obtenerTodos();
        return ResponseEntity.ok(productos); // 200 OK
    }

    // --- 2. GET PRODUCTO POR ID ---
    // Seguridad: Accesible por todos
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> obtenerPorId(@PathVariable Long id) {
        ProductoDTO producto = productoService.obtenerPorId(id);
        return ResponseEntity.ok(producto); // 200 OK
    }
    
    // --- 3. CREAR NUEVO PRODUCTO ---
    // Seguridad: Solo accesible por ADMIN/EMPLEADO
    @PostMapping
    public ResponseEntity<ProductoDTO> crear(@Valid @RequestBody ProductoDTO dto) {
        ProductoDTO nuevoProducto = productoService.guardarProducto(dto);
        // Usamos ResponseEntity.status(HttpStatus.CREATED) para devolver 201
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto); 
    }
    
    // --- 4. CREAR VARIOS PRODUCTOS EN LOTE ---
    // Seguridad: Solo accesible por ADMIN/EMPLEADO
    @PostMapping("/lote")
    public ResponseEntity<List<ProductoDTO>> crearLote(@Valid @RequestBody List<ProductoDTO> dtos) {
        List<ProductoDTO> nuevosProductos = productoService.guardarTodos(dtos);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevosProductos); // 201 Created
    }
    
    // --- 5. ACTUALIZAR PRODUCTO EXISTENTE ---
    // Seguridad: Solo accesible por ADMIN/EMPLEADO
    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> actualizar(
            @PathVariable Long id, 
            @Valid @RequestBody ProductoDTO dto) {
            
        ProductoDTO actualizado = productoService.actualizarProducto(id, dto);
        return ResponseEntity.ok(actualizado); // 200 OK
    }
    
    // --- 6. ELIMINAR PRODUCTO POR ID ---
    // Seguridad: Solo accesible por ADMIN/EMPLEADO
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        // Usamos ResponseEntity.noContent() para devolver 204 No Content
        return ResponseEntity.noContent().build(); 
    }
}