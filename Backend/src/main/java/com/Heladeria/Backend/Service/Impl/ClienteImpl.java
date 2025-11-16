package com.Heladeria.Backend.Service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // Necesario para la seguridad
import org.springframework.stereotype.Service;    // Necesario para la seguridad
import org.springframework.transaction.annotation.Transactional;

import com.Heladeria.Backend.DTO.ClienteRequest; // Necesario para la seguridad
import com.Heladeria.Backend.DTO.ClienteResponse;
import com.Heladeria.Backend.Repository.ClienteRepository; // Necesario para cifrar
import com.Heladeria.Backend.Repository.CuentaRepository;
import com.Heladeria.Backend.Service.ClienteService;
import com.Heladeria.Backend.model.Cliente;
import com.Heladeria.Backend.model.Cuenta;
import com.Heladeria.Backend.model.Rol;

@Service
public class ClienteImpl implements ClienteService { // Nombre de clase corregido

    private final ClienteRepository clienteRepository;
    private final CuentaRepository cuentaRepository;
     private final PasswordEncoder passwordEncoder; 

    @Autowired
    public ClienteImpl(ClienteRepository clienteRepository, 
                              CuentaRepository cuentaRepository, // Inyectar CuentaRepository
                              PasswordEncoder passwordEncoder) { // Inyectar PasswordEncoder
        this.clienteRepository = clienteRepository;
        this.cuentaRepository = cuentaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public ClienteResponse registrarCliente(ClienteRequest registroDTO) {
        // --- 1. Crear y cifrar la Cuenta ---
        Cuenta cuenta = new Cuenta();
        cuenta.setUsuario(registroDTO.getUsuario());
        // CRÍTICO: Cifrar la contraseña
        cuenta.setContrasena(passwordEncoder.encode(registroDTO.getContrasena())); 
        cuenta.setRol(Rol.CLIENTE);
        
        // --- 2. Crear la entidad Cliente y vincular la Cuenta ---
        Cliente cliente = mapToEntity(registroDTO);
        cliente.setCuenta(cuenta); // Vincular One-to-One
        
        // --- 3. Persistir ---
        Cliente guardado = clienteRepository.save(cliente);
        
        // --- 4. Mapear a DTO de Respuesta ---
        return mapToResponseDTO(guardado);
    }

    @Transactional(readOnly = true)
    @Override
    public ClienteResponse obtenerClientePorId(Long id) { // Nombre de método actualizado
        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + id));
            
        return mapToResponseDTO(cliente);
    }
    
    // Asumiendo que has añadido este método a la interfaz:
    @Transactional(readOnly = true)
    @Override
    public List<ClienteResponse> obtenerTodosClientes() {
        return clienteRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }


    // --- Métodos de Mapeo (Helpers) ---
    private Cliente mapToEntity(ClienteRequest registroDTO) {
        Cliente cliente = new Cliente();
        cliente.setNombre(registroDTO.getNombre());
        cliente.setApellido(registroDTO.getApellido());
        cliente.setTelefono(registroDTO.getTelefono());
        cliente.setDireccion(registroDTO.getDireccion());
        // La cuenta se vincula en el método registrarCliente
        return cliente;
    }
    
    private ClienteResponse mapToResponseDTO(Cliente cliente) {
        if (cliente == null) return null;
        return new ClienteResponse(
            cliente.getId(),
            cliente.getNombre(),
            cliente.getApellido(),
            cliente.getTelefono(),
            cliente.getDireccion(),
            // Obtener el usuario de la cuenta (seguro)
            cliente.getCuenta() != null ? cliente.getCuenta().getUsuario() : null
        );
    }
}
