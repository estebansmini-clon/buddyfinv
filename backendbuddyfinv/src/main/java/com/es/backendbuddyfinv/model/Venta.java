package com.es.backendbuddyfinv.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "ventas")
@Data
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venta")
    private Long idVenta;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "total", nullable = false)
    private double total;

    // Relaciones
    @ManyToOne
    @JoinColumn(name = "id_metodo_pago", nullable = false)
    private MetodoPago metodoPago;

    @ManyToOne
    @JoinColumn(name = "id_estado_venta", nullable = false)
    private EstadoVenta estadoVenta;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    //propietario del negocio
    @ManyToOne
    @JoinColumn(name = "id_propietario")
    private Usuario propietario;

    @OneToMany(mappedBy = "venta")
    private List<DetalleVenta> detalleVentas;

    // Constructor por defecto
    public Venta() {
    }

    // Constructor con parámetros
    public Venta(LocalDate fecha, double total) {
        this.fecha = fecha;
        this.total = total;
    }

}
