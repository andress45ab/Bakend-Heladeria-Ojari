package com.Heladeria.Backend.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Heladeria.Backend.model.Cuenta;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    
    // Buscar la cuenta por el nombre de usuario (o email) para el login (Spring Security)
    Optional<Cuenta> findByUsuario(String usuario);

    // Verificar si ya existe un usuario (útil al registrar)
    boolean existsByUsuario(String usuario);
}