package com.Heladeria.Backend.Controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page; // Tu paquete
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping; // Usamos Page
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.Heladeria.Backend.DTO.PedidoRequest;
import com.Heladeria.Backend.DTO.PedidoResponse;
import com.Heladeria.Backend.Service.PedidoService;
import com.Heladeria.Backend.model.Estado;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    @Autowired
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    // --- 1. CREAR NUEVO PEDIDO (POST) ---
    // Protegido por Rol: CLIENTE
    @PostMapping
    public ResponseEntity<PedidoResponse> crearPedido(@Valid @RequestBody PedidoRequest pedidoRequest){
        
        PedidoResponse nuevoPedido = pedidoService.crearPedido(pedidoRequest);

        // Devolvemos 201 Created y la URI del recurso
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(nuevoPedido.getId())
                .toUri();
        
        return ResponseEntity.created(location).body(nuevoPedido); 
    }

    // --- 2. OBTENER DETALLE POR ID (GET) ---
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> obtenerPedidoPorId(@PathVariable Long id){
        // Debe haber lógica de seguridad para que solo vea su pedido (o Admin/Empleado)
        PedidoResponse pedido = pedidoService.obtenerPedidoPorId(id);
        return ResponseEntity.ok(pedido);
    }

    // --- 3. OBTENER HISTORIAL DE CLIENTE (PAGINADO) ---
    // CORREGIDO: El servicio devuelve Page, no List.
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<Page<PedidoResponse>> obtenerPedidosPorCliente(
            @PathVariable Long clienteId, 
            Pageable pageable) {
            
        // CORREGIDO: El nombre del método coincide con la interfaz
        Page<PedidoResponse> pedidos = pedidoService.obtenerPedidosPorCliente(clienteId, pageable);
        return ResponseEntity.ok(pedidos);

    }

    // --- 4. ACTUALIZAR ESTADO DEL PEDIDO (PATCH) ---
    // Protegido por Roles: ADMIN/EMPLEADO
    @PatchMapping("/{id}/estado")
    public ResponseEntity<PedidoResponse> actualizarEstadoDelPedido(
            @PathVariable Long id, 
            @RequestParam Estado estado) {
            
        PedidoResponse pedidoActualizado = pedidoService.actualizarEstadoPedido(id, estado);
        return ResponseEntity.ok(pedidoActualizado);
    }

    // --- 5. OBTENER PEDIDOS POR ESTADO (ADMIN) ---
    // El servicio devuelve una List<PedidoResponse>, eliminamos Pageable aquí por ahora.
    @GetMapping("/estado")
    public ResponseEntity<List<PedidoResponse>> obtenerPedidosPorEstado(
            @RequestParam Estado estado) {
            
        List<PedidoResponse> pedidos = pedidoService.obtenerPedidosPorEstado(estado);
        return ResponseEntity.ok(pedidos);
    }
}