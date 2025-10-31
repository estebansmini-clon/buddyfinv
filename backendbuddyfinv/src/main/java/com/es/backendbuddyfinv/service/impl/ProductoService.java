package com.es.backendbuddyfinv.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.es.backendbuddyfinv.model.Producto;
import com.es.backendbuddyfinv.repository.ProductoRepository;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    // Crear un nuevo producto
    public Producto createProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    // Obtener todos los productos
    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    // Obtener un producto por ID
    public Optional<Producto> getProductoById(Long id) {
        return productoRepository.findById(id);
    }

    // Actualizar un producto
    public Producto updateProducto(Long id, Producto productoDetails) {
        Optional<Producto> optionalProducto = productoRepository.findById(id);
        if (optionalProducto.isPresent()) {
            Producto producto = optionalProducto.get();
            // Aquí puedes actualizar los campos específicos del producto
            // producto.setCampo(productoDetails.getCampo());
            return productoRepository.save(producto);
        }
        return null;
    }

    // Eliminar un producto
    public boolean deleteProducto(Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Verificar si existe un producto
    public boolean existsById(Long id) {
        return productoRepository.existsById(id);
    }
}
