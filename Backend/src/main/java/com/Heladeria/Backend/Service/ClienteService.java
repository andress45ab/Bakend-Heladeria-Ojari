package com.Heladeria.Backend.Service;



import java.util.List;

import com.Heladeria.Backend.DTO.ClienteRequest;
import com.Heladeria.Backend.DTO.ClienteResponse;

public interface ClienteService {

    /**
     * Registra un nuevo cliente, creando la entidad Cliente y su Cuenta asociada
     * con la contraseña cifrada y el rol CLIENTE.
     * @param registroDTO DTO con los datos personales y credenciales.
     * @return ClienteResponse del cliente creado.
     */
    ClienteResponse registrarCliente(ClienteRequest registroDTO);

    /**
     * Obtiene los detalles no sensibles de un cliente por su ID.
     * @param id El ID del cliente.
     * @return ClienteResponseDTO.
     */
    ClienteResponse obtenerClientePorId(Long id); 

    /**
     * Obtiene la lista de todos los clientes registrados (útil para administración).
     * @return Lista de ClienteResponseDTO.
     */
    List<ClienteResponse> obtenerTodosClientes();
}