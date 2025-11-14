package com.es.backendbuddyfinv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.es.backendbuddyfinv.repository.InventarioRepository;
import com.es.backendbuddyfinv.service.impl.ProductoService;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.stream.Collectors;
import com.es.backendbuddyfinv.dto.ProductoDTO;
import com.es.backendbuddyfinv.model.Producto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import com.es.backendbuddyfinv.security.CustomUserDetails;
import com.es.backendbuddyfinv.security.JwtAuthenticationFilter;
import jakarta.persistence.EntityNotFoundException;


import java.util.List;

import com.es.backendbuddyfinv.dto.EgresoDTO;
import com.es.backendbuddyfinv.dto.ProductoCrearDTO;


@CrossOrigin(origins="http://localhost:5173")
//para aceptar las peticiones desde el frontend
@RestController
@RequestMapping("/productos")

   

public class ProductoController {

    //@Autowired
    //para inyectar el repositorio de productos

    //private ProductoRepository productoRepository;
    //endpoint para obtener todos los productos

     @Autowired
    private ProductoService productoService ;

    
    @Autowired
    private InventarioRepository inventarioRepository; //creo que aqui no debe ir este repository pero por ahora lo dejare ahi para no romper nada
/*
@GetMapping("/propietario")
        public ResponseEntity<List<EgresoDTO>> obtenerEgresosDetallados() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
    
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Long idPropietario = userDetails.getIdUsuario();
    
            List<EgresoDTO> egresos = egresoService.listarDTOsPorUsuario(idPropietario);
            return ResponseEntity.ok(egresos);
        }
 **/
    @GetMapping("/test")
    public String test() {
        return "Controlador de productos funcionando correctamente!";
    }
    //se supone que este metodo no debe ir aqui, lo pondr ene comentarios mientras tanto
@GetMapping("/propietario")
        public ResponseEntity<List<ProductoDTO>> obtenerEgresosDetallados() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
    
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Long idPropietario = userDetails.getIdUsuario();
    
            List<ProductoDTO> producto = productoService.getProductosPorUsuario(idPropietario);
                                            
            return ResponseEntity.ok(producto);
        }

    //funcion para agregar productos
    @PostMapping("/agregar")
    public ResponseEntity<?> crearProducto(@RequestBody ProductoCrearDTO dto) {
        try {
            // Obtener el usuario autenticado del contexto de seguridad
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            
            if (auth == null || !auth.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                     .body("Usuario no autenticado");
            }
            
            // Obtener el ID del usuario del CustomUserDetails
            Long propietarioId = null;
            if (auth.getPrincipal() instanceof CustomUserDetails) {
                CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
                propietarioId = userDetails.getIdUsuario();
            }
            
            if (propietarioId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                     .body("No se pudo obtener el ID del usuario");
            }
            
            Producto nuevo = productoService.createProducto(dto, propietarioId);
            return ResponseEntity.ok(nuevo);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Tipo de producto no encontrado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al guardar el producto: " + e.getMessage());
        }
    }

    /** 
    @GetMapping("/mine")
    public ResponseEntity<List<ProductoDTO>> obtenerMisProductos() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); // este es el subject del token
    }**/

         /**List<Producto> productos = ProductoRepository.findByPropietarioUsuario(username);
    List<ProductoDTO> productosDTO = productos.stream().map(p -> {
        ProductoDTO dto = new ProductoDTO();
        dto.setIdProducto(p.getIdProducto());
        dto.setNombre(p.getNombre());
        dto.setPrecio(p.getPrecio());
        dto.setTipoProducto(p.getTipoProducto()!=null ? p.getTipoProducto().getNombre() : "Sin tipo");
        dto.setEstadoProducto(p.getEstadoProducto()!=null ? p.getEstadoProducto().getNombre() : "Sin estado");
        dto.setPropietario(p.getPropietario()!=null ? p.getPropietario().getNombre() : "Sin propietario");
        return dto;
    }).collect(Collectors.toList());

    return ResponseEntity.ok(productosDTO);
}**/


}
