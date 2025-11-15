package com.es.backendbuddyfinv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.es.backendbuddyfinv.model.TipoEgreso;

@Repository
public interface TipoEgresoRepository extends JpaRepository<TipoEgreso, Long> {
}

