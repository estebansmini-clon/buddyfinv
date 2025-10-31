package com.es.backendbuddyfinv.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tipo_egresos")
@Data
public class TipoEgreso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_egreso")
    private Long idTipoEgreso;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    // Constructor por defecto
    public TipoEgreso() {
    }

    // Constructor con parámetros
    public TipoEgreso(String descripcion) {
        this.descripcion = descripcion;
    }

}
