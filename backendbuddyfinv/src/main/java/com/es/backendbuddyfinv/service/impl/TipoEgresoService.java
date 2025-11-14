package com.es.backendbuddyfinv.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.es.backendbuddyfinv.model.TipoEgreso;
import com.es.backendbuddyfinv.repository.TipoEgresoRepository;

@Service
public class TipoEgresoService {

    @Autowired
    private TipoEgresoRepository tipoEgresoRepository;

    // Crear un nuevo tipo de egreso
    public TipoEgreso createTipoEgreso(TipoEgreso tipoEgreso) {
        return tipoEgresoRepository.save(tipoEgreso);
    }

    // Obtener todos los tipos de egreso
    public List<TipoEgreso> getAllTipoEgresos() {
        return tipoEgresoRepository.findAll();
    }

    // Obtener un tipo de egreso por ID
    public Optional<TipoEgreso> getTipoEgresoById(Long id) {
        return tipoEgresoRepository.findById(id);
    }

    // Actualizar un tipo de egreso
    public TipoEgreso updateTipoEgreso(Long id, TipoEgreso tipoEgresoDetails) {
        Optional<TipoEgreso> optionalTipoEgreso = tipoEgresoRepository.findById(id);
        if (optionalTipoEgreso.isPresent()) {
            TipoEgreso tipoEgreso = optionalTipoEgreso.get();
            // Aquí puedes actualizar los campos específicos del tipo de egreso
            // tipoEgreso.setCampo(tipoEgresoDetails.getCampo());
            return tipoEgresoRepository.save(tipoEgreso);
        }
        return null;
    }

    // Eliminar un tipo de egreso
    public boolean deleteTipoEgreso(Long id) {
        if (tipoEgresoRepository.existsById(id)) {
            tipoEgresoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Verificar si existe un tipo de egreso
    public boolean existsById(Long id) {
        return tipoEgresoRepository.existsById(id);
    }
}
