package com.Heladeria.Backend.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Heladeria.Backend.model.Estado;
import com.Heladeria.Backend.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
    // Listado paginado de pedidos de un cliente
    Page<Pedido> findByClienteIdOrderByFechaPedidoDesc(Long clienteId, Pageable pageable);

    // Listado paginado filtrando por cliente y estado
    Page<Pedido> findByClienteIdAndEstadoOrderByFechaPedidoDesc(Long clienteId, Estado estado, Pageable pageable);

    // Listado no paginado (todo) de un cliente
    List<Pedido> findByClienteIdOrderByFechaPedidoDesc(Long clienteId);

    // Rango por fecha para reportes
    List<Pedido> findByFechaPedidoBetween(LocalDateTime inicio, LocalDateTime fin);

    // Detalle de un pedido cargando detalles (ítems) y productos con FETCH JOIN
@Query("""
    select p
    from Pedido p
    left join fetch p.detalles d 
    left join fetch d.producto prod
    where p.id = :id
    """)
Optional<Pedido> findDetalleById(@Param("id") Long id);
    
    // Opcional: obtener todos los pedidos paginados por estado (útil para el admin dashboard)
    Page<Pedido> findByEstadoOrderByFechaPedidoAsc(Estado estado, Pageable pageable);
}