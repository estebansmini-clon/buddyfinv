package com.es.backendbuddyfinv.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GastoTipoEgresoDTO {
    private String tipoEgreso;   // Ej: "Salarios", "Materiales"
    private Double montoTotal;  // SUM(e.costo)
}