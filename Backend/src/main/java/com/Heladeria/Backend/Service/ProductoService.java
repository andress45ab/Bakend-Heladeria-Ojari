package com.Heladeria.Backend.Service;



import java.util.List;

import com.Heladeria.Backend.DTO.ProductoDTO;

public interface ProductoService {

    /**
     * Obtiene una lista de todos los productos disponibles (el menú).
     * @return Lista de ProductoDTO.
     */
    List<ProductoDTO> obtenerTodos();

    /**
     * Obtiene un producto específico por su ID.
     * @param id El ID del producto.
     * @return ProductoDTO.
     */
    ProductoDTO obtenerPorId(Long id);

    /**
     * Crea un nuevo producto.
     * @param dto ProductoDTO con los datos a guardar.
     * @return ProductoDTO guardado con ID generado.
     */
    ProductoDTO guardarProducto(ProductoDTO dto);

    /**
     * Guarda una lista de productos en una sola transacción (creación en lote).
     * @param dtos Lista de ProductoDTO.
     * @return Lista de ProductoDTO guardados.
     */
    List<ProductoDTO> guardarTodos(List<ProductoDTO> dtos);

    /**
     * Actualiza la información de un producto existente.
     * @param id ID del producto a actualizar.
     * @param dto ProductoDTO con los datos actualizados.
     * @return ProductoDTO actualizado.
     */
    ProductoDTO actualizarProducto(Long id, ProductoDTO dto);

    /**
     * Elimina un producto por su ID.
     * @param id ID del producto a eliminar.
     */
    void eliminarProducto(Long id);
}