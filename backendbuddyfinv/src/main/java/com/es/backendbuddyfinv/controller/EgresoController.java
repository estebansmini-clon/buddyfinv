package com.es.backendbuddyfinv.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.es.backendbuddyfinv.dto.EgresoDTO;
import com.es.backendbuddyfinv.model.Egreso;
import com.es.backendbuddyfinv.model.Usuario;
import com.es.backendbuddyfinv.security.CustomUserDetails;
import com.es.backendbuddyfinv.service.impl.EgresoService;
import com.es.backendbuddyfinv.service.impl.UsuarioService;






@RestController
@RequestMapping("/Egresos")
public class EgresoController {
    
    @Autowired
    EgresoService egresoService;

    @Autowired
    UsuarioService usuarioService;

        // 🔹 Obtener todos los egresos del propietario autenticado
        @GetMapping("/propietario")
        public ResponseEntity<List<Egreso>> obtenerEgresosPorPropietario() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
    
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Long idPropietario = userDetails.getIdUsuario();
    
            List<Egreso> egresos = egresoService.ListarEgresosXusuario(idPropietario);
            return ResponseEntity.ok(egresos);
        }

        @GetMapping("/total")
        public ResponseEntity<Double> obtenerTotalEgresosPorUsuario() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
    
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Long idPropietario = userDetails.getIdUsuario();
    
            double total = egresoService.costoTotalEgresosXusuario(idPropietario);
            return ResponseEntity.ok(total);
        }


    //yaaaaaaaaaaaaaaaaaaaaaaaaa
    //agrega los egresos
    @PostMapping("/agregarEgreso")
    public ResponseEntity<?> registrarEgreso(@RequestBody Egreso egreso) {
        try {
            if(egreso.getCosto()<0){
                return ResponseEntity.badRequest().body("El egreso no puede ser un numero negativo");
            }
         
            Egreso nuevoEgreso= egresoService.createEgreso(egreso);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEgreso);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al registra rel egreso: " + e.getMessage());
        }
    }
    //este me trae todos los egresos de todos los usuarios
    //yaaaaaaaaaaaaaaaaaaaaaa
    @GetMapping("/obtenerTegresos")
    public ResponseEntity<List<EgresoDTO>>  traerTodosLosEgresos() {
        try {
            List<Egreso> tEgresos=egresoService.getAllEgresos();
            System.out.println("estos son todos los egresos encontrados"+ tEgresos.size());
            
            //se convierte la tabla tEgresos a un DTO (para que solo me traiga la informacion especifica y no todas las tablas que estan relacionadas con esa tabla)
            List<EgresoDTO> egresoDto= tEgresos.stream().map(EgresoDTO::new).collect(Collectors.toList());
            
           return ResponseEntity.ok(egresoDto);


        } catch (Exception e) {
            //devuelve una excepcion 
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            
        }
            
       
    }
    //yaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.ok("Backend funcionando correctamente");
    }


    //yaaaaaaaaaaaaaaaaaaaaaaaa
    @PostMapping("/valida/{id}")
    public ResponseEntity<Egreso> ObtenerEgresosById(@PathVariable Long id) { 
            Optional<Egreso> egreso= egresoService.getEgresoById(id);
            return egreso.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());

    }
    //modifica el egreso
    //yaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
    @PutMapping("/modificar/{id}")
    public ResponseEntity<?> modificarEgreso(@PathVariable Long id, @RequestBody Egreso egresoDetails) {
        try {
            
            Optional<Egreso> egresoOptional= egresoService.getEgresoById(id);

            if(egresoOptional.isEmpty()){
                return ResponseEntity.notFound().build();

            }
            Egreso egreso =egresoOptional.get();


            if(egresoDetails.getCosto() != null)egreso.setCosto(egresoDetails.getCosto());
            if(egresoDetails.getMetodoPago() != null)egreso.setMetodoPago(egresoDetails.getMetodoPago());
            if(egresoDetails.getObservacion()!= null)egreso.setObservacion(egreso.getObservacion());
            if(egresoDetails.getObservacion()!= null)egreso.setObservacion(egreso.getObservacion());
            
            Egreso EgresoActualizado = egresoService.updateEgreso(id, egreso);

            return ResponseEntity.ok(EgresoActualizado);

        } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        
        
        
        }
        
        
    }
    //yaaaaaaaaaaaaaaaaaaaaaaaa
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarEgreso(@PathVariable Long id){
        try {
            boolean eliminado=egresoService.deleteEgreso(id);
            if(eliminado){
                return ResponseEntity.ok().body("El egreso ha sido borrado con exito");
            }
            return ResponseEntity.notFound().build();


        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar el egreso: " + e.getMessage());
        
        
        }

    }

    //este me trae todos los egresos de un usuario en especifico
    //yaaaaaaaaaaaaaaaaaaaaaaaaaa
    @GetMapping("obtenetXusuario/{id}")
    public ResponseEntity<List<EgresoDTO>> getAllEgresosById(@PathVariable Long id) {
        try {
            List<Egreso> egresosLista= egresoService.ListarEgresosXusuario(id);
            System.out.println("estos son todos los egresos encontrados"+ egresosLista.size());
            
            List<EgresoDTO> egresoDto= egresosLista.stream().map(EgresoDTO::new).collect(Collectors.toList());
            
            return ResponseEntity.ok(egresoDto);
            
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        
    }
    
    @GetMapping("obtenerTotal/{id}")
    public ResponseEntity<?> totalCostoEgresos(@PathVariable Long id) {
        try {
            Optional<Usuario> usuario = usuarioService.getUsuarioById(id);
            
            if (usuario.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado con ID: " + id);
            }
            
            Double costoTotal = egresoService.costoTotalEgresosXusuario(usuario.get().getId());
            
            return ResponseEntity.ok(costoTotal);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al calcular el costo total de egresos: " + e.getMessage());
        }
    }
    



}
