package com.Heladeria.Backend.DTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Incluye getters, setters, y toString
@NoArgsConstructor // Constructor vacío
@AllArgsConstructor // Constructor lleno
public class DetallePedidoResponse {

    // Identificador del detalle
    private Long id; 
    
    // Referencia al producto
    private Long productoId; 
    private String nombreProducto;
    private String imagenProducto;
    
    // Detalles de la venta
    private Integer cantidad;
    private BigDecimal precioUnit;
    private BigDecimal subtotal;

    public void setId(Long id) {
        this.id = id;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public void setImagenProducto(String imagenProducto) {
        this.imagenProducto = imagenProducto;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecioUnit(BigDecimal precioUnit) {
        this.precioUnit = precioUnit;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
