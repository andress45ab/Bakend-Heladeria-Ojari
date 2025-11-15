package com.Heladeria.Backend.DTO;

import com.Heladeria.Backend.model.Rol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Incluye getters, setters, y toString
@NoArgsConstructor // Constructor vacío
@AllArgsConstructor // Constructor lleno
public class EmpleadoResponse {
    
    private Long id;
    private String nombre;
    private String email; // Usamos el email del registro
   
    
    // Información CLAVE de la Cuenta, sin la contraseña:
    private String usuario;
    private Rol rol; // El rol que tiene asignado (EMPLEADO o ADMIN)
}