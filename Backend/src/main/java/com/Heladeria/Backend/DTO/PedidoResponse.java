package com.Heladeria.Backend.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.Heladeria.Backend.model.Estado;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Incluye getters, setters, y toString
@NoArgsConstructor // Constructor vacío
@AllArgsConstructor // Constructor lleno
public class PedidoResponse {

    private Long id;
    private Long clienteId;
    private LocalDateTime fechaPedido;
    private BigDecimal total;
    private Estado estado;
    
    // Lista de ítems/detalles del pedido (usando el DTO anidado)
    private List<DetallePedidoResponse> items; 
}