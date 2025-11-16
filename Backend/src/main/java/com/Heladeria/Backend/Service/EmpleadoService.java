package com.Heladeria.Backend.Service;

import java.util.List;

import com.Heladeria.Backend.DTO.EmpleadoRequest;
import com.Heladeria.Backend.DTO.EmpleadoResponse;

public interface EmpleadoService {

    /**
     * Registra un nuevo empleado, creando la entidad Empleado y su Cuenta asociada
     * con la contraseña cifrada y el rol EMPLEADO (o ADMIN, si es el primero).
     * @param registroDTO DTO con los datos personales y credenciales.
     * @return EmpleadoResponseDTO del empleado creado.
     */
    EmpleadoResponse registrarEmpleado(EmpleadoRequest registroDTO);

    /**
     * Obtiene los detalles no sensibles de un empleado por su ID.
     * @param id El ID del empleado.
     * @return EmpleadoResponseDTO.
     */
    EmpleadoResponse obtenerEmpleadoPorId(Long id); 

    /**
     * Obtiene la lista de todos los empleados registrados (solo para administración).
     * @return Lista de EmpleadoResponseDTO.
     */
    List<EmpleadoResponse> obtenerTodosEmpleados();
}