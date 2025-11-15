package com.Heladeria.Backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Heladeria.Backend.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    // Aquí puedes añadir métodos de consulta personalizados si son necesarios en el futuro,
    // como buscar clientes por teléfono o dirección.
  // Ejemplo: Optional<Cliente> findByTelefono(String telefono);
}
