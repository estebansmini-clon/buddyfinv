package com.es.backendbuddyfinv.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "productos")
@Data
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long idProducto;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "precio", nullable = false)
    private double precio;

    // Relaciones
    @ManyToOne
    @JoinColumn(name = "id_tipo_producto", nullable = false)
    private TipoProducto tipoProducto;

    @ManyToOne
    @JoinColumn(name = "id_estado_producto", nullable = false)
    private EstadoProducto estadoProducto;

    @OneToOne(mappedBy = "producto")
    private Inventario inventario;
    

    //propietario del negocio
    @ManyToOne
    @JoinColumn(name = "id_propietario")
    private Usuario propietario;

    // Constructor por defecto
    public Producto() {
    }

    // Constructor con parámetros
    public Producto(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

}
