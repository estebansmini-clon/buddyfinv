package com.es.backendbuddyfinv.dto;

import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentasSerieDTO {
    private String period;          // Ej: "2025-12" o "2025-12-03"
    private BigDecimal totalVentas; // SUM(v.total)
}