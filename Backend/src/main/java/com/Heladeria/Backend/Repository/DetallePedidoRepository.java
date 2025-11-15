package com.Heladeria.Backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository; // Usamos el nombre final de la entidad
import org.springframework.stereotype.Repository;

import com.Heladeria.Backend.model.DetallePedido;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {
    
    // Aquí podrías añadir métodos para buscar detalles por el ID del pedido:
    // List<DetallePedido> findByPedidoId(Long pedidoId);
}
