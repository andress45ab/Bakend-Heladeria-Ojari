package com.Heladeria.Backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Heladeria.Backend.model.Producto;
import java.util.List;
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    // Si se necesitara buscar productos por nombre o filtrar por categoría, 
    // se añadirían métodos aquí, por ejemplo:
    // List<Producto> findByNombreContainingIgnoreCase(String nombre);

    // Método que Spring Data JPA infiere automáticamente (buscar por el campo 'categoria' y su 'id')
    List<Producto> findByCategoriaId(Long categoriaId);
}
