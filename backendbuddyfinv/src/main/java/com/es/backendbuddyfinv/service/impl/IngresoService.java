package com.es.backendbuddyfinv.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.es.backendbuddyfinv.model.Ingreso;
import com.es.backendbuddyfinv.repository.IngresoRepository;

@Service
public class IngresoService {

    @Autowired
    private IngresoRepository ingresoRepository;

    // Crear un nuevo ingreso
    public Ingreso createIngreso(Ingreso ingreso) {
        return ingresoRepository.save(ingreso);
    }

    // Obtener todos los ingresos
    public List<Ingreso> getAllIngresos() {
        return ingresoRepository.findAll();
    }

    // Obtener un ingreso por ID
    public Optional<Ingreso> getIngresoById(Long id) {
        return ingresoRepository.findById(id);
    }

    // Actualizar un ingreso
    public Ingreso updateIngreso(Long id, Ingreso ingresoDetails) {
        Optional<Ingreso> optionalIngreso = ingresoRepository.findById(id);
        if (optionalIngreso.isPresent()) {
            Ingreso ingreso = optionalIngreso.get();
            // Aquí puedes actualizar los campos específicos del ingreso
            // ingreso.setCampo(ingresoDetails.getCampo());
            return ingresoRepository.save(ingreso);
        }
        return null;
    }

    // Eliminar un ingreso
    public boolean deleteIngreso(Long id) {
        if (ingresoRepository.existsById(id)) {
            ingresoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Verificar si existe un ingreso
    public boolean existsById(Long id) {
        return ingresoRepository.existsById(id);
    }
}
