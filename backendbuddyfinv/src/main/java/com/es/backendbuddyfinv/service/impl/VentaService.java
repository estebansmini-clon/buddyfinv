package com.es.backendbuddyfinv.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.es.backendbuddyfinv.model.Venta;
import com.es.backendbuddyfinv.repository.VentaRepository;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    // Crear una nueva venta
    public Venta createVenta(Venta venta) {
        return ventaRepository.save(venta);
    }

    // Obtener todas las ventas
    public List<Venta> getAllVentas() {
        return ventaRepository.findAll();
    }

    // Obtener una venta por ID
    public Optional<Venta> getVentaById(Long id) {
        return ventaRepository.findById(id);
    }

    // Actualizar una venta
    public Venta updateVenta(Long id, Venta ventaDetails) {
        Optional<Venta> optionalVenta = ventaRepository.findById(id);
        if (optionalVenta.isPresent()) {
            Venta venta = optionalVenta.get();
            // Aquí puedes actualizar los campos específicos de la venta
            // venta.setCampo(ventaDetails.getCampo());
            return ventaRepository.save(venta);
        }
        return null;
    }

    // Eliminar una venta
    public boolean deleteVenta(Long id) {
        if (ventaRepository.existsById(id)) {
            ventaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Verificar si existe una venta
    public boolean existsById(Long id) {
        return ventaRepository.existsById(id);
    }
}
