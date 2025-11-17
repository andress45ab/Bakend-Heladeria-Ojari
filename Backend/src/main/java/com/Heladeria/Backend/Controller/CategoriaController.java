package com.Heladeria.Backend.Controller;

import com.Heladeria.Backend.model.Categoria;
import com.Heladeria.Backend.Repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    // 1. OBTENER TODAS LAS CATEGORÍAS (Público, para la navegación)
    @GetMapping
    public List<Categoria> getAllCategorias() {
        return categoriaRepository.findAll();
    }

    // 2. CREAR UNA NUEVA CATEGORÍA (Solo para Administradores)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')") // Asumiendo que ADMIN crea nuevas categorías
    public ResponseEntity<Categoria> createCategoria(@RequestBody Categoria categoria) {
        Categoria nuevaCategoria = categoriaRepository.save(categoria);
        return new ResponseEntity<>(nuevaCategoria, HttpStatus.CREATED);
    }
    
    // ... Puedes añadir DELETE y PUT aquí, también protegidos con @PreAuthorize
}