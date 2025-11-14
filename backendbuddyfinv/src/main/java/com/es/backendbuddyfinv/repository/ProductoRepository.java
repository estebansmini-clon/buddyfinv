package com.es.backendbuddyfinv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.es.backendbuddyfinv.model.Producto;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @Query("""
    SELECT DISTINCT p FROM Producto p
    JOIN FETCH p.tipoProducto
    JOIN FETCH p.estadoProducto
    JOIN FETCH p.propietario
    JOIN FETCH p.detalleInventarios d
    WHERE p.propietario.id = :idPropietario
""")
List<Producto> findByPropietarioConInventario(@Param("idPropietario") Long idPropietario);
    
}
