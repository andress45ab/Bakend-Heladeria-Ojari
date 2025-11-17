package com.Heladeria.Backend.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.Heladeria.Backend.model.Estado;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, y toString
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
   
    public Long getId() { // <-- Añade este método
        return id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public LocalDateTime getFechaPedido() {
        return fechaPedido;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Estado getEstado() {
        return estado;
    }

    public List<DetallePedidoResponse> getItems() {
        return items;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public void setFechaPedido(LocalDateTime fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void setItems(List<DetallePedidoResponse> items) {
        this.items = items;
    }
    
    



}