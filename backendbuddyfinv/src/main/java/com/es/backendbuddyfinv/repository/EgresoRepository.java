package com.es.backendbuddyfinv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.es.backendbuddyfinv.model.Egreso;

@Repository
public interface EgresoRepository extends JpaRepository<Egreso, Long> {
}