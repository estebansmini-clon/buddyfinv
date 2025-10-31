package com.es.backendbuddyfinv.dto;

import com.es.backendbuddyfinv.model.Rol;
import lombok.Data;

@Data
public class UsuarioDTO {
    private Long id;
    private String nitUsuario;
    private String nombre;
    private String email;
    private String usuario;
    private String negocio;
    private Rol rol;
    private String administradorNombre; // Solo el nombre del administrador
    
    // Constructor para convertir de Usuario a UsuarioDTO
    public UsuarioDTO(com.es.backendbuddyfinv.model.Usuario usuario) {
        this.id = usuario.getId();
        this.nitUsuario = usuario.getNitUsuario();
        this.nombre = usuario.getNombre();
        this.email = usuario.getEmail();
        this.usuario = usuario.getUsuario();
        this.negocio = usuario.getNegocio();
        this.rol = usuario.getRol();
        
        // Solo incluir el nombre del administrador, no el objeto completo
        if (usuario.getAdministrador() != null) {
            this.administradorNombre = usuario.getAdministrador().getNombre();
        }
    }
}
