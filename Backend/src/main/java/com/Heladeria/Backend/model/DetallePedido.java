package com.Heladeria.Backend.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detalles_pedido") // Nombre de tabla estandarizado
@Data // Genera getters, setters, etc.
@NoArgsConstructor // Constructor vacío
@AllArgsConstructor // Constructor con todos los campos
public class DetallePedido { // Clase renombrada

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación al Pedido (ManyToOne)
    @NotNull(message = "El pedido no puede ser nulo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    // Relación al Producto (ManyToOne)
    @NotNull(message = "El producto no puede ser nulo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    // Cantidad
    @Min(value = 1, message = "La cantidad minima es 1")
    @Column(nullable = false)
    private int cantidad;

    // Precio Unitario al momento de la venta (para historial)
    @NotNull(message = "El precio no puede ser nulo")
    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnit;

    // Método que calcula el subtotal (NO es una columna en la DB)
    @Transient 
    public BigDecimal getSubtotal() {
        return precioUnit.multiply(BigDecimal.valueOf(cantidad));
    }
}