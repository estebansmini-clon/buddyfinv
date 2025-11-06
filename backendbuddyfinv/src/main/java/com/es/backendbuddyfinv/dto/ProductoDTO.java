package com.es.backendbuddyfinv.dto;

import com.es.backendbuddyfinv.model.EstadoProducto;
import com.es.backendbuddyfinv.model.Inventario;
import com.es.backendbuddyfinv.model.TipoProducto;
import com.es.backendbuddyfinv.model.Usuario;


import lombok.Data;

@Data
public class ProductoDTO {
    private Long id;
    private String nombre;
    private double precio;
    private String tipoProducto;
    private String estadoProducto;
    private String propietario;

    // Constructor para convertir de Producto a ProductoDTO
    public ProductoDTO(com.es.backendbuddyfinv.model.Producto producto) {
        this.id = producto.getIdProducto();
        this.nombre = producto.getNombre();
        this.precio = producto.getPrecio();
        this.tipoProducto = producto.getTipoProducto().getObservacion();
        this.estadoProducto = producto.getEstadoProducto().getObservacion();
        this.propietario = producto.getPropietario().getNombre();
    }
}
