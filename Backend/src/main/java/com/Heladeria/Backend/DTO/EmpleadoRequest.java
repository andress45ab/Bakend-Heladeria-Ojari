package com.Heladeria.Backend.DTO;

import com.Heladeria.Backend.model.Rol; // Importar la enum Rol
import jakarta.validation.constraints.*;
import lombok.Data; 
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data // Incluye getters, setters, y toString
@NoArgsConstructor // Constructor vacío
@AllArgsConstructor // Constructor lleno
public class EmpleadoRequest {
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 80)
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 80)
    private String apellido;

    @Size(max = 30)
    private String telefono;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Email inválido")
    @Size(max = 100)
    private String email;

    @Size(max = 100)
    private String puesto;

    // Credenciales para la Cuenta
    @NotBlank(message = "El usuario es obligatorio")
    @Size(min = 3, max = 100)
    private String usuario;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, max = 100)
    private String contrasena;
    
    // Rol (Necesario para la seguridad)
    @NotNull(message = "El rol del empleado es obligatorio")
    private Rol rol; 

    public String getUsuario() {
        return usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getNombre() {
        return nombre;
    }
}
