package com.es.backendbuddyfinv.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "metodo_pagos")
@Data
public class MetodoPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_metodo_pago")
    private int idMetodoPago;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    // Constructor por defecto
    public MetodoPago() {
    }

    // Constructor con parámetros
    public MetodoPago(String descripcion) {
        this.descripcion = descripcion;
    }

}
