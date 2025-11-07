
package com.es.backendbuddyfinv.dto;
import lombok.Data;

@Data


public class EgresoDTO{
    private Long id_egreso;
    private double costo;
    private String observacion;
    private String descripcionDMetodoDpago;
    private Long id_propietario;
    private String fecha;
    // ERROR ORIGINAL: El campo se llamaba "DescripcionTegreso" (con mayúscula)
    // Jackson por defecto serializa propiedades a camelCase, por lo que "DescripcionTegreso" 
    // se convertía en "descripcionTegreso" en el JSON, pero el frontend buscaba "DescripcionTegreso"
    // SOLUCIÓN: Cambiar el nombre del campo a camelCase estándar "descripcionTegreso" 
    // para que coincida con la serialización de Jackson y el frontend pueda accederlo correctamente
    private String descripcionTegreso;

    public EgresoDTO(com.es.backendbuddyfinv.model.Egreso egreso){
        this.id_egreso=egreso.getIdEgreso();
        this.costo=egreso.getCosto();
        this.observacion=egreso.getObservacion();
        this.descripcionDMetodoDpago = egreso.getMetodoPago() != null ? egreso.getMetodoPago().getDescripcion() : null;
        // Formatear la fecha a String para el frontend
        this.fecha = egreso.getFecha() != null ? egreso.getFecha().toString() : null;
       
        if (egreso.getTipoEgreso() != null) {
            this.descripcionTegreso = egreso.getTipoEgreso().getDescripcion();
            System.out.println("TipoEgreso cargado: " + this.descripcionTegreso);
        } else {
            this.descripcionTegreso = null;
            System.out.println("ADVERTENCIA: TipoEgreso es null para egreso ID: " + this.id_egreso);
        }
    }



    
}