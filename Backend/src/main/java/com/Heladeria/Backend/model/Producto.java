package com.Heladeria.Backend.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType; // Añadir Lombok
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table; // Añadir AllArgsConstructor
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "productos")
@Data // Genera getters, setters, etc.
@NoArgsConstructor // Constructor vacío
@AllArgsConstructor // Constructor con todos los campos
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "No puede haber un nombre de producto vacío")
    @Size(min = 2, max = 200, message = "El nombre debe tener entre 2 y 200 caracteres")
    @Column(nullable = false, length = 200)
    private String nombre;

    @NotNull(message = "El precio no puede ser nulo")
    @Column(nullable = false, precision = 10, scale = 2) // PRECISION monetaria
    private BigDecimal precio;

    @Size(max = 500, message = "La descripción no puede tener más de 500 caracteres")
    @Column(length = 500)
    private String descripcion;

    @Column(length = 800)
    private String imagenUrl;

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    // --- NUEVO CAMPO DE CATEGORÍA ---
    @ManyToOne(fetch = FetchType.LAZY) // Relación de muchos a uno
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    // --- Asegúrate de añadir los getters y setters para 'categoria' ---

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}