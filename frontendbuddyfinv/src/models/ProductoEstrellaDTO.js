// models/ProductoEstrellaDTO.js
export class ProductoEstrellaDTO {
    constructor({ nombreProducto, cantidadVendida }) {
      this.nombreProducto = nombreProducto;   // Nombre del producto
      this.cantidadVendida = cantidadVendida; // Cantidad total vendida
    }
  }