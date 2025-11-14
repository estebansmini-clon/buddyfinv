package com.es.backendbuddyfinv.repository;

import com.es.backendbuddyfinv.model.EstadoProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoProductoRepository extends JpaRepository<EstadoProducto, Long> {
    EstadoProducto findByObservacion(String observacion);
}
