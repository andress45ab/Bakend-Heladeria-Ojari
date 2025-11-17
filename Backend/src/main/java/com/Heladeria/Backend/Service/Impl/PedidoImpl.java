package com.Heladeria.Backend.Service.Impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; // Interfaz necesaria
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Heladeria.Backend.DTO.DetallePedidoRequest;
import com.Heladeria.Backend.DTO.DetallePedidoResponse;
import com.Heladeria.Backend.DTO.PedidoRequest;
import com.Heladeria.Backend.DTO.PedidoResponse;
import com.Heladeria.Backend.Repository.ClienteRepository;
import com.Heladeria.Backend.Repository.DetallePedidoRepository;
import com.Heladeria.Backend.Repository.PedidoRepository;
import com.Heladeria.Backend.Repository.ProductoRepository;
import com.Heladeria.Backend.Service.PedidoService;
import com.Heladeria.Backend.model.Cliente;
import com.Heladeria.Backend.model.DetallePedido;
import com.Heladeria.Backend.model.Estado;
import com.Heladeria.Backend.model.Pedido;
import com.Heladeria.Backend.model.Producto;

@Service
public class PedidoImpl implements PedidoService { // Nombre de clase y contrato corregidos

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;
    private final DetallePedidoRepository detallePedidoRepository; // Asumiendo que esta es la entidad de detalle

    @Autowired
    public PedidoImpl(PedidoRepository pedidoRepository,
                             ClienteRepository clienteRepository,
                             ProductoRepository productoRepository,
                             DetallePedidoRepository detallePedidoRepository) { // Inyectar detalle repo
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.productoRepository = productoRepository;
        this.detallePedidoRepository = detallePedidoRepository;
    }

    @Transactional
    @Override
    public PedidoResponse crearPedido(PedidoRequest pedidoRequest) {
        Cliente cliente = clienteRepository.findById(pedidoRequest.getClienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + pedidoRequest.getClienteId()));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setEstado(Estado.PENDIENTE);
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setTotal(BigDecimal.ZERO);

        BigDecimal totalPedido = BigDecimal.ZERO;

        for (DetallePedidoRequest itemRequest : pedidoRequest.getItems()) {
            Producto producto = productoRepository.findById(itemRequest.getProductoId())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + itemRequest.getProductoId()));

            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedido);
            detalle.setProducto(producto);
            detalle.setCantidad(itemRequest.getCantidad());
            detalle.setPrecioUnit(producto.getPrecio());

            totalPedido = totalPedido.add(detalle.getPrecioUnit().multiply(BigDecimal.valueOf(detalle.getCantidad())));

            pedido.getDetalles().add(detalle);
        }

        pedido.setTotal(totalPedido);
        Pedido guardado = pedidoRepository.save(pedido);
        return mapToResponse(guardado);
    }

    @Transactional(readOnly = true)
    @Override
    public PedidoResponse obtenerPedidoPorId(Long id) {
         Pedido pedido = pedidoRepository.findById(id)
                 .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado con ID: " + id));
         return mapToResponse(pedido);
    }
    
    // NOTA: Aquí faltarían los otros métodos de la interfaz (obtenerPedidosPorCliente, actualizarEstado, etc.)


    // --- Métodos de Mapeo (Helpers) ---
    private PedidoResponse mapToResponse(Pedido pedido) {
        if (pedido == null) return null;

        List<DetallePedidoResponse> itemDTOs = pedido.getDetalles() == null ? List.of() :
            pedido.getDetalles().stream()
                .map(this::mapDetalleToDTO)
                .collect(Collectors.toList());

        PedidoResponse dto = new PedidoResponse();
        dto.setId(pedido.getId());
        dto.setClienteId(pedido.getCliente() != null ? pedido.getCliente().getId() : null);
        dto.setFechaPedido(pedido.getFechaPedido());
        dto.setTotal(pedido.getTotal());
        dto.setEstado(pedido.getEstado());
        dto.setItems(itemDTOs);
        return dto;
    }

    private DetallePedidoResponse mapDetalleToDTO(DetallePedido detalle) {
        DetallePedidoResponse dto = new DetallePedidoResponse();
        dto.setId(detalle.getId());
        Producto prod = detalle.getProducto();
        dto.setProductoId(prod != null ? prod.getId() : null);
        dto.setNombreProducto(prod != null ? prod.getNombre() : null);
        dto.setImagenProducto(prod != null ? prod.getImagenUrl() : null);
        dto.setCantidad(detalle.getCantidad());
        dto.setPrecioUnit(detalle.getPrecioUnit());
        dto.setSubtotal(detalle.getSubtotal());
        return dto;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<PedidoResponse> obtenerPedidosPorCliente(Long clienteId, Pageable pageable) {
        Page<Pedido> page = pedidoRepository.findByClienteIdOrderByFechaPedidoDesc(clienteId, pageable);
        return page.map(this::mapToResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PedidoResponse> obtenerPedidosPorEstado(Estado estado) {
        return pedidoRepository
                .findByEstadoOrderByFechaPedidoAsc(estado, Pageable.unpaged())
                .map(this::mapToResponse)
                .getContent();
    }

    @Transactional
    @Override
    public PedidoResponse actualizarEstadoPedido(Long pedidoId, Estado nuevoEstado) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado con ID: " + pedidoId));
        pedido.setEstado(nuevoEstado);
        Pedido guardado = pedidoRepository.save(pedido);
        return mapToResponse(guardado);
    }


    @Transactional(readOnly = true)
    @Override
    public Estado obtenerEstadoDePedido(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado con ID: " + pedidoId));
        return pedido.getEstado();
    }
}