package com.Heladeria.Backend.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.Heladeria.Backend.DTO.PedidoRequest;
import com.Heladeria.Backend.DTO.PedidoResponse;
import com.Heladeria.Backend.model.Estado;

public interface PedidoService {

    /**
     * Procesa y crea un nuevo pedido.
     * @param pedidoRequest DTO que contiene el cliente ID y la lista de ítems.
     * @return El PedidoResponse del pedido creado con totales calculados.
     */
    PedidoResponse crearPedido(PedidoRequest pedidoRequest);

    /**
     * Obtiene el detalle completo de un pedido por su ID.
     * @param pedidoId El ID del pedido.
     * @return El PedidoResponse con todos sus detalles.
     */
    PedidoResponse obtenerPedidoPorId(Long pedidoId);

    /**
     * Obtiene todos los pedidos de un cliente (usando paginación).
     * Este es el método preferido para obtener el historial.
     * @param clienteId ID del cliente.
     * @param pageable Configuración de paginación (página, tamaño, orden).
     * @return Página de PedidoResponse.
     */
    Page<PedidoResponse> obtenerPedidosPorCliente(Long clienteId, Pageable pageable);

    /**
     * Obtiene todos los pedidos filtrando por un estado específico (para la administración).
     * @param estado El estado por el cual filtrar (PENDIENTE, ENTREGADO, etc.).
     * @return Lista de PedidoResponse.
     */
    List<PedidoResponse> obtenerPedidosPorEstado(Estado estado);
    
    /**
     * Actualiza el estado de un pedido (ej. de PENDIENTE a EN PREPARACION).
     * @param pedidoId ID del pedido a actualizar.
     * @param nuevoEstado El nuevo estado.
     * @return El PedidoResponse actualizado.
     */
    PedidoResponse actualizarEstadoPedido(Long pedidoId, Estado nuevoEstado);

    /**
     * Obtiene el Estado actual de un Pedido por su ID.
     * @param pedidoId ID del pedido.
     * @return El Estado del Pedido.
     */
    Estado obtenerEstadoDePedido(Long pedidoId);
}

