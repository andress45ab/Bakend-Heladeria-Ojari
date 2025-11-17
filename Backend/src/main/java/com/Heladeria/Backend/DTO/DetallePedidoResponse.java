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
}
