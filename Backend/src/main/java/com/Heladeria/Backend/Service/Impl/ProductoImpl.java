package com.Heladeria.Backend.Service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Heladeria.Backend.DTO.ProductoDTO;
import com.Heladeria.Backend.Repository.ProductoRepository;
import com.Heladeria.Backend.Service.ProductoService;
import com.Heladeria.Backend.model.Producto;

@Service
public class ProductoImpl implements ProductoService { // Nombre corregido

    private final ProductoRepository productoRepository;

    @Autowired // Usamos @Autowired para inyección por constructor
    public ProductoImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // --- 1. CREAR UN PRODUCTO ---
    @Transactional
    @Override
    public ProductoDTO guardarProducto(ProductoDTO dto) {
        // Usamos el helper mapToEntity
        Producto producto = mapToEntity(dto);
        
        Producto guardado = productoRepository.save(producto);
        return mapToDTO(guardado); // Usamos mapToDTO para consistencia
    }

    // --- 2. CREAR VARIOS PRODUCTOS EN LOTE ---
    @Transactional
    @Override
    public List<ProductoDTO> guardarTodos(List<ProductoDTO> dtos) {
        // Mapeamos DTOs a Entidades usando el helper
        List<Producto> entidades = dtos.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());

        List<Producto> guardados = productoRepository.saveAll(entidades);
        
        // Mapeamos las entidades guardadas a DTOs
        return guardados.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // --- 3. OBTENER POR ID ---
    @Transactional(readOnly = true)
    @Override
    public ProductoDTO obtenerPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + id));
        return mapToDTO(producto);
    }

    // --- 4. OBTENER TODOS ---
    @Transactional(readOnly = true)
    @Override
    public List<ProductoDTO> obtenerTodos() {
        return productoRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // --- 5. ELIMINAR PRODUCTO ---
    @Transactional
    @Override
    public void eliminarProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            // Usamos la excepción específica de Spring si la tienes, si no, IllegalArgumentException está bien.
            throw new IllegalArgumentException("Producto no encontrado con ID: " + id);
        }
        productoRepository.deleteById(id);
    }

    // --- 6. ACTUALIZAR PRODUCTO ---
    @Transactional
    @Override
    public ProductoDTO actualizarProducto(Long id, ProductoDTO dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + id));

        // Actualizamos campos:
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setDescripcion(dto.getDescripcion());
        producto.setImagenUrl(dto.getImagenUrl());

        Producto actualizado = productoRepository.save(producto);
        return mapToDTO(actualizado);
    }

    // --- MÉTODOS DE MAPEO (HELPERS) ---

    // Mapea Entidad a DTO (Respuesta)
    private ProductoDTO mapToDTO(Producto producto) {
        return new ProductoDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getPrecio(),
                producto.getDescripcion(),
                producto.getImagenUrl()
        );
    }
    
    // Mapea DTO a Entidad (Recepción de Datos)
    private Producto mapToEntity(ProductoDTO dto) {
        Producto producto = new Producto();
        // Nota: No mapeamos el ID, ya que es para la creación (POST)
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setDescripcion(dto.getDescripcion());
        producto.setImagenUrl(dto.getImagenUrl());
        return producto;
    }
}


