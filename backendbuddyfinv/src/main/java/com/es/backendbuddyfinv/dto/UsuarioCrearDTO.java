package com.es.backendbuddyfinv.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioCrearDTO {
    private String nitUsuario;
    private String nombre;
    private String email;
    private String usuario;
    private String password;

}
