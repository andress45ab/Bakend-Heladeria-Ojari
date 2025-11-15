package com.Heladeria.Backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Heladeria.Backend.model.Empleado;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    // Si la entidad Empleado tiene un campo 'puesto' o se necesita buscar por algún atributo específico, 
    // se añadiría aquí, por ejemplo:
    // List<Empleado> findByPuesto(String puesto);
}