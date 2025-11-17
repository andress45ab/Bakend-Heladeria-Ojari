package com.Heladeria.Backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Incluye getters, setters, y toString
@NoArgsConstructor // Constructor vacío
@AllArgsConstructor // Constructor lleno
public class ClienteResponse {
    
    private Long id;
    private String nombre;
    private String apellido;
    private String telefono;
    private String direccion;
    private String Usuario;
    // Opcional: private String usuario; // Para mostrar el email de login
    public Long getId() {
        return id;
    }
}