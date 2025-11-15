package com.Heladeria.Backend.DTO;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Incluye getters, setters, y toString
@NoArgsConstructor // Constructor vacío
@AllArgsConstructor // Constructor lleno
public class PedidoRequest {

    @NotNull(message = "El ID del cliente no puede ser nulo")
    private Long clienteId; 

    @Valid
    @NotEmpty(message = "Debe haber al menos un producto en el pedido")
    private List<DetallePedidoRequest> items;
}