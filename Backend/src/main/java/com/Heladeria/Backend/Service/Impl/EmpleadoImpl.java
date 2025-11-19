package com.Heladeria.Backend.Service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Heladeria.Backend.DTO.EmpleadoRequest;
import com.Heladeria.Backend.DTO.EmpleadoResponse; // Interfaz necesaria
import com.Heladeria.Backend.Repository.CuentaRepository; // Necesario para cifrar
import com.Heladeria.Backend.Repository.EmpleadoRepository;
import com.Heladeria.Backend.Service.EmpleadoService;
import com.Heladeria.Backend.model.Cuenta;
import com.Heladeria.Backend.model.Empleado;
import com.Heladeria.Backend.model.Rol;

@Service
public class EmpleadoImpl implements EmpleadoService { // Implementa la interfaz

    private final EmpleadoRepository empleadoRepository;
    private final CuentaRepository cuentaRepository;
    private final PasswordEncoder passwordEncoder; 

    @Autowired
    public EmpleadoImpl(EmpleadoRepository empleadoRepository, 
                              CuentaRepository cuentaRepository,
                              PasswordEncoder passwordEncoder) {
        this.empleadoRepository = empleadoRepository;
        this.cuentaRepository = cuentaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public EmpleadoResponse registrarEmpleado(EmpleadoRequest registroDTO) { // Firma corregida
        
        // 1. Crear y cifrar la Cuenta
    Cuenta cuenta = new Cuenta();
    cuenta.setUsuario(registroDTO.getUsuario());
    cuenta.setContrasena(passwordEncoder.encode(registroDTO.getContrasena())); 

    // 💥 CORRECCIÓN: USAR EL ROL DEL DTO DE ENTRADA 💥
    Rol rolAUsar = Rol.valueOf(registroDTO.getRol()); // Asumiendo que EmpleadoRequest tiene getRol()
    cuenta.setRol(rolAUsar); // <-- AHORA PUEDE SER ADMIN O EMPLEADO

    // 2. Crear la entidad Empleado y vincular la Cuenta
    Empleado empleado = mapToEntity(registroDTO); // Mapeo ahora es completo
    empleado.setCuenta(cuenta); 

    // 3. Persistir 
    Empleado guardado = empleadoRepository.save(empleado);

    // 4. Mapear a DTO de Respuesta
    return mapToResponseDTO(guardado);
    }

    @Transactional(readOnly = true)
    @Override
    public EmpleadoResponse obtenerEmpleadoPorId(Long id) { // Nombre de método estandarizado
        Empleado empleado = empleadoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado con ID: " + id));
            
        return mapToResponseDTO(empleado);
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<EmpleadoResponse> obtenerTodosEmpleados() { // Nombre de método estandarizado
        return empleadoRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

   // EmpleadoImpl.java (Método mapToEntity CORREGIDO)
private Empleado mapToEntity(EmpleadoRequest registroDTO) {
    Empleado empleado = new Empleado();
    empleado.setNombre(registroDTO.getNombre());
    empleado.setApellido(registroDTO.getApellido()); // <-- ¡AGREGAR ESTO!
    empleado.setTelefono(registroDTO.getTelefono()); // <-- ¡AGREGAR ESTO!
    
    // Si necesitas EMAIL en la entidad Empleado:
    // empleado.setEmail(registroDTO.getEmail()); 
    
    return empleado;
}
 private EmpleadoResponse mapToResponseDTO(Empleado empleado) {
    if (empleado == null) return null;
    
    // CRÍTICO: Usamos el operador ternario para acceder a los campos de Cuenta
    // y evitar un NullPointerException si la cuenta es nula.
    String usuario = empleado.getCuenta() != null ? empleado.getCuenta().getUsuario() : null;
    Rol rol = empleado.getCuenta() != null ? empleado.getCuenta().getRol() : null;
    
    // Asumo que 'email' y 'usuario' son el mismo campo de Cuenta.getUsuario().
    // Esto es común si la Cuenta se registra con un email. Si son diferentes, 
    // tendrías que haber mapeado el email también en la entidad.

    return new EmpleadoResponse( // Asumo que EmpleadoResponse es EmpleadoResponseDTO
        empleado.getId(),
        empleado.getNombre(),
        empleado.getApellido(),
        usuario, // Argumento 4: email (Usando el campo 'usuario' de la Cuenta)
        usuario, // Argumento 5: usuario (Usando el mismo campo 'usuario' de la Cuenta)
        rol      // Argumento 6: rol (Usando getRol() — singular y correcto)
    );
}

}
