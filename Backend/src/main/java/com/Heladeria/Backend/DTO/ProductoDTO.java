package com.Heladeria.Backend.DTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Incluye getters, setters, y toString
@NoArgsConstructor // Constructor vacío
@AllArgsConstructor // Constructor lleno
public class ProductoDTO {
    
    private Long id;
    private String nombre;
    private BigDecimal precio;
    private String descripcion;
    private String imagenUrl;
}
