package com.es.backendbuddyfinv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import com.es.backendbuddyfinv.model.DetalleVenta;

@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {
    List<DetalleVenta> findByVentaIdVenta(Long idVenta);
    //////////////santiago productos estrella
    @Query("""
        SELECT dv.producto.nombre, SUM(dv.cantidad)
        FROM DetalleVenta dv
        JOIN dv.venta v
        WHERE v.propietario.id = :idPropietario
        AND v.estadoVenta.observacion LIKE '%PAGADA%'
        GROUP BY dv.producto.nombre
        ORDER BY SUM(dv.cantidad) DESC
    """)
    List<Object[]> findProductosEstrella(@Param("idPropietario") Long idPropietario);
    ///////////////fin santiago productos estrella

    List<DetalleVenta> findByProductoIdProducto(Long idProducto);
}
