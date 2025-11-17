package com.Heladeria.Backend.Controller;

import java.util.List;
import java.util.Optional; // Necesario para el filtro opcional

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Seguridad
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam; // Filtro
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
    
    // 1. GET TODOS LOS PRODUCTOS (Menú) - AHORA CON FILTRO OPCIONAL
    @GetMapping
    public ResponseEntity<List<ProductoDTO>> listarTodos(@RequestParam Optional<Long> categoriaId) {
        // Llama al servicio con el filtro (el servicio maneja si está presente o no)
        List<ProductoDTO> productos = productoService.obtenerTodos(categoriaId); 
        return ResponseEntity.ok(productos); // 200 OK
    }

    // 2. GET PRODUCTO POR ID (Público)
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> obtenerPorId(@PathVariable Long id) {
        ProductoDTO producto = productoService.obtenerPorId(id);
        return ResponseEntity.ok(producto); // 200 OK
    }
    
    // 3. CREAR NUEVO PRODUCTO (ADMIN/EMPLEADO)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLEADO')") 
    public ResponseEntity<ProductoDTO> crear(@Valid @RequestBody ProductoDTO dto) {
        // El DTO debe contener el campo 'categoriaId'
        ProductoDTO nuevoProducto = productoService.guardarProducto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto); // 201 Created
    }
    
    // 4. CREAR VARIOS PRODUCTOS EN LOTE (ADMIN/EMPLEADO)
    @PostMapping("/lote")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLEADO')") 
    public ResponseEntity<List<ProductoDTO>> crearLote(@Valid @RequestBody List<ProductoDTO> dtos) {
        List<ProductoDTO> nuevosProductos = productoService.guardarTodos(dtos);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevosProductos); // 201 Created
    }
    
    // 5. ACTUALIZAR PRODUCTO EXISTENTE (ADMIN/EMPLEADO)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLEADO')") 
    public ResponseEntity<ProductoDTO> actualizar(
            @PathVariable Long id, 
            @Valid @RequestBody ProductoDTO dto) {
            
        ProductoDTO actualizado = productoService.actualizarProducto(id, dto);
        return ResponseEntity.ok(actualizado); // 200 OK
    }
    
    // 6. ELIMINAR PRODUCTO POR ID (ADMIN)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // Usamos solo ADMIN para DELETES por ser más destructivo
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}