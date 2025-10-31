package com.es.backendbuddyfinv.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "egresos")
@Data
public class Egreso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_egreso")
    private Long idEgreso;

    @Column(name = "costo", nullable = false)
    private double costo;

    @Column(name = "observacion")
    private String observacion;

    // Relaciones
    @ManyToOne
    @JoinColumn(name = "id_tipo_egreso", nullable = false)
    private TipoEgreso tipoEgreso;

    @ManyToOne
    @JoinColumn(name = "id_metodo_pago", nullable = false)
    private MetodoPago metodoPago;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    //propietario del negocio
    @ManyToOne
    @JoinColumn(name = "id_propietario")
    private Usuario propietario;

    // Constructor por defecto
    public Egreso() {
    }

    // Constructor con parámetros
    public Egreso(double costo, String observacion) {
        this.costo = costo;
        this.observacion = observacion;
    }

}
