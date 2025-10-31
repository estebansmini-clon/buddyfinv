package com.es.backendbuddyfinv.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "estado_ventas")
@Data
public class EstadoVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_venta")
    private int idEstadoVenta;

    @Column(name = "observacion")
    private String observacion;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    // Constructor por defecto
    public EstadoVenta() {
    }

    // Constructor con parámetros
    public EstadoVenta(String observacion, int cantidad) {
        this.observacion = observacion;
        this.cantidad = cantidad;
    }

}
