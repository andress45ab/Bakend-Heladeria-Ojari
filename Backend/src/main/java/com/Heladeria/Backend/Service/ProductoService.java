package com.Heladeria.Backend.Service;

import java.util.List;
import java.util.Optional;

import com.Heladeria.Backend.DTO.ProductoDTO;

public interface ProductoService {

    /**
     * Obtiene una lista de todos los productos disponibles, con filtro opcional por categoría.
     *
     * @param categoriaId ID opcional de la categoría para filtrar.
     * @return lista de productos.
     */
    List<ProductoDTO> obtenerTodos(Optional<Long> categoriaId);

    /**
     * Obtiene un producto por su ID.
     *
     * @param id ID del producto.
     * @return producto solicitado.
     */
    ProductoDTO obtenerPorId(Long id);

    /**
     * Crea un nuevo producto.
     * El DTO debe incluir el ID de la categoría.
     *
     * @param dto datos del producto a guardar.
     * @return producto guardado con ID generado.
     */
    ProductoDTO guardarProducto(ProductoDTO dto);

    /**
     * Guarda varios productos en una sola transacción.
     *
     * @param dtos lista de productos.
     * @return lista de productos guardados.
     */
    List<ProductoDTO> guardarTodos(List<ProductoDTO> dtos);

    /**
     * Actualiza un producto existente.
     *
     * @param id ID del producto a actualizar.
     * @param dto datos actualizados del producto.
     * @return producto actualizado.
     */
    ProductoDTO actualizarProducto(Long id, ProductoDTO dto);

    /**
     * Elimina un producto por su ID.
     *
     * @param id ID del producto a eliminar.
     */
    void eliminarProducto(Long id);

}

