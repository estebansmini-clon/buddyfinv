// models/GastoTipoEgresoDTO.js
export class GastoTipoEgresoDTO {
    constructor({ tipoEgreso, montoTotal }) {
      this.tipoEgreso = tipoEgreso;   // Ej: "Salarios", "Materiales"
      this.montoTotal = montoTotal;   // SUM(e.costo)
    }
  }