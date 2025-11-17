package com.Heladeria.Backend.Service.Impl;

import java.util.List;
import java.util.Optional; // <-- Necesario para obtenerProductos filtrado
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Heladeria.Backend.DTO.ProductoDTO;
import com.Heladeria.Backend.Repository.CategoriaRepository; // <-- NUEVO: Repositorio de Categoría
import com.Heladeria.Backend.Repository.ProductoRepository;
import com.Heladeria.Backend.Service.ProductoService;
import com.Heladeria.Backend.model.Categoria; // <-- NUEVO: Entidad Categoría
import com.Heladeria.Backend.model.Producto;

@Service
public class ProductoImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository; // <-- NUEVO: Inyección

    @Autowired
    public ProductoImpl(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository; // <-- Inicialización
    }

    // --- 1. CREAR UN PRODUCTO ---
    @Transactional
    @Override
    // NOTA: Asumo que tu ProductoService tiene un método que acepta el categoriaId, 
    // como hicimos en el ejemplo anterior (ProductoService.save(ProductoDTO dto, Long categoriaId))
    public ProductoDTO guardarProducto(ProductoDTO dto) { 
        // 🚨 CAMBIO CRÍTICO: Necesitamos el ID de la categoría aquí. 
        // Si el DTO ya lo tiene (lo más limpio), lo extraemos:
        Long categoriaId = dto.getCategoriaId(); 
        
        Producto producto = mapToEntity(dto);
        
        // 1. BUSCAR Y ASIGNAR CATEGORÍA
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada con ID: " + categoriaId));
        producto.setCategoria(categoria); // Asignación
        
        Producto guardado = productoRepository.save(producto);
        return mapToDTO(guardado);
    }

    // --- 2. CREAR VARIOS PRODUCTOS EN LOTE ---
    @Transactional
    @Override
    public List<ProductoDTO> guardarTodos(List<ProductoDTO> dtos) {
        // En un lote, esto se vuelve más complejo si cada DTO tiene un ID de categoría diferente.
        // Simplificaremos la lógica asumiendo que el mapeo DTO->Entity maneja la categoría:
        
        List<Producto> entidades = dtos.stream()
                .map(dto -> {
                    Producto producto = mapToEntity(dto);
                    // 1. BUSCAR Y ASIGNAR CATEGORÍA para cada item
                    Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                            .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada."));
                    producto.setCategoria(categoria);
                    return producto;
                })
                .collect(Collectors.toList());

        List<Producto> guardados = productoRepository.saveAll(entidades);
        return guardados.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    // NOTA: Tendrás que modificar tu interfaz ProductoService para que obtenerTodos() soporte un filtro
    /*
    // --- 4. OBTENER TODOS (con filtro opcional) ---
    @Transactional(readOnly = true)
    @Override
    public List<ProductoDTO> obtenerTodos(Optional<Long> categoriaId) { // <-- Se acepta Optional
        List<Producto> productos;
        
        if (categoriaId.isPresent()) {
            // Requiere que el ProductoRepository tenga el método findByCategoriaId(Long id)
            productos = productoRepository.findByCategoriaId(categoriaId.get()); 
        } else {
            productos = productoRepository.findAll();
        }
        
        return productos.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    */


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

        // 🚨 CAMBIO CRÍTICO: ACTUALIZAR CATEGORÍA si el DTO la incluye
        if (dto.getCategoriaId() != null) {
            Categoria nuevaCategoria = categoriaRepository.findById(dto.getCategoriaId())
                    .orElseThrow(() -> new IllegalArgumentException("Categoría de actualización no encontrada."));
            producto.setCategoria(nuevaCategoria);
        }

        Producto actualizado = productoRepository.save(producto);
        return mapToDTO(actualizado);
    }

    // --- MÉTODOS DE MAPEO (HELPERS) ---

    // Mapea Entidad a DTO (Respuesta)
    private ProductoDTO mapToDTO(Producto producto) {
        // 🚨 CAMBIO: Incluir el ID de la Categoría en el DTO de respuesta
        return new ProductoDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getPrecio(),
                producto.getDescripcion(),
                producto.getImagenUrl(),
                producto.getCategoria().getId() // <-- NUEVO: ID de la categoría
        );

    }
    
    // Mapea DTO a Entidad (Recepción de Datos)
    private Producto mapToEntity(ProductoDTO dto) {
        Producto producto = new Producto();
        // Nota: El mapeo de la Categoría se hace FUERA de este método helper (en save/update)
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setDescripcion(dto.getDescripcion());
        producto.setImagenUrl(dto.getImagenUrl());
        return producto;
    }
    
    // --- MÉTODOS NO MODIFICADOS (obtenerPorId, eliminarProducto, obtenerTodos sin filtro) ---
    // ... mantienes el resto del código ...
    
    // NOTA: Para no romper la interfaz, dejo obtenerTodos sin modificar, pero recomiendo añadir la versión filtrada.
    @Transactional(readOnly = true)
    @Override
    public List<ProductoDTO> obtenerTodos(Optional<Long> categoriaId) {
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
        throw new IllegalArgumentException("Producto no encontrado con ID: " + id);
    }
    productoRepository.deleteById(id);
}
// --- 3. OBTENER POR ID ---
@Transactional(readOnly = true)
@Override
public ProductoDTO obtenerPorId(Long id) {
    Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + id));
    return mapToDTO(producto);
}

}