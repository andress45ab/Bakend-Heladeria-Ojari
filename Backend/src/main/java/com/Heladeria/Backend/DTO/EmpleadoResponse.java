package com.Heladeria.Backend.DTO;

import com.Heladeria.Backend.model.Rol;
import lombok.Data;
import lombok.NoArgsConstructor;
// import lombok.AllArgsConstructor; // REMOVIDA: Entra en conflicto con el constructor manual

@Data // Genera getters, setters, y toString
@NoArgsConstructor // Constructor vacío
public class EmpleadoResponse {
    
    private Long id;
    private String nombre;
    private String apellido;
    private String email; 
    
    // Información CLAVE de la Cuenta, sin la contraseña:
    private String usuario;
    private Rol rol; // El rol que tiene asignado (EMPLEADO o ADMIN)

    /**
     * Constructor manual para el mapeo desde la entidad Empleado.
     * (6 Argumentos)
     */
    public EmpleadoResponse(Long id, String nombre, String apellido, String email, String usuario, Rol rol) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.usuario = usuario;
        this.rol = rol;
    }
}