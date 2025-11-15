package com.Heladeria.Backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Heladeria.Backend.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    // Si se necesitara buscar productos por nombre o filtrar por categoría, 
    // se añadirían métodos aquí, por ejemplo:
    // List<Producto> findByNombreContainingIgnoreCase(String nombre);
}
