package com.es.backendbuddyfinv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.es.backendbuddyfinv.model.Ingreso;

@Repository
public interface IngresoRepository extends JpaRepository<Ingreso, Long> {
}
