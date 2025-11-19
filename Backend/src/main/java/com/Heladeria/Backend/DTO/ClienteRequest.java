package com.Heladeria.Backend.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@NoArgsConstructor // Constructor vacio
@AllArgsConstructor // Constructor lleno
public class ClienteRequest {
    
    @NotBlank(message = "El nombre no puede estar vacío.")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres.")
    private String nombre;

    @Size(max = 50, message = "El apellido no debe exceder los 50 caracteres.")
    private String apellido;
    
    @Size(max = 20, message = "El teléfono no debe exceder los 20 caracteres.")
    private String telefono;

    @Size(max = 255, message = "La dirección no debe exceder los 255 caracteres.")
    private String direccion;

    // Campos de la Cuenta
    @NotBlank(message = "El nombre de usuario no puede estar vacio")
    @Email(message = "Debe ser un formato de correo válido") // Añadir esta validación es CLAVE
    @Size(min = 5, max = 100, message = "El nombre de usuario debe tener entre 5 y 100 caracteres")
    private String usuario;

    @NotBlank(message = "La contraseña no puede estar vacia")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String contrasena; // Corregido a contrasena (camelCase)

    public String getUsuario() {
        return usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    // ¡AGREGAR ESTO! Asegúrate de que este campo exista si tu entidad Cliente lo usa
   @NotBlank(message = "El email no puede estar vacío.") 
   @Email(message = "Debe ser un formato de correo válido")
   private String email;

   public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String rol; // Añadir si el JSON lo envía

    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }
}