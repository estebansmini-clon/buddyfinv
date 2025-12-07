// models/VentasSerieDTO.js
export class VentasSerieDTO {
    constructor({ period, totalVentas }) {
      this.period = period;          // Ej: "2025-12" o "2025-12-03"
      this.totalVentas = totalVentas; // SUM(v.total)
    }
  }