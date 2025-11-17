package com.Heladeria.Backend.DTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // Constructor vacío
public class ProductoDTO {
    
    private Long id;
    private String nombre;
    private BigDecimal precio;
    private String descripcion;
    private String imagenUrl;
    private Long categoriaId; // <--- ¡Añade este campo!

    public Long getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }
    public BigDecimal getPrecio() {
        return precio;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public String getImagenUrl() {
        return imagenUrl;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }
    public Long getCategoriaId() {
        return categoriaId;
    }
    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    // Asegúrate de que los tipos coincidan con lo que estás pasando en mapToDTO:
public ProductoDTO(Long id, String nombre, BigDecimal precio, String descripcion, String imagenUrl, Long    categoriaId) {
    this.id = id;
    this.nombre = nombre;
    this.precio = precio;
    this.descripcion = descripcion;
    this.imagenUrl = imagenUrl;
    this.categoriaId = categoriaId;
}
    
    

}
