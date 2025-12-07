package com.es.backendbuddyfinv.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoEstrellaDTO {
    private String nombreProducto;   // Nombre del producto
    private Long cantidadVendida;    // Cantidad total vendida (SUM(dv.cantidad))
}