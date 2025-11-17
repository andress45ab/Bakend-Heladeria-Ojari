package com.Heladeria.Backend.Repository;

import com.Heladeria.Backend.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    // Puedes añadir métodos de búsqueda si los necesitas, ej:
    // Optional<Categoria> findByNombre(String nombre);
}