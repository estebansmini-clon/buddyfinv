package com.es.backendbuddyfinv.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioEdicionDTO {
    
    // nitUsuario NO se incluirá para edición (es solo lectura en la UI)
    @Size(min = 2, max = 100, message = "El nombre debe tener máximo 100 caracteres")
    private String nombre;

    @Email(message = "Formato de email inválido")
    @Size(max = 100, message = "El email debe tener máximo 100 caracteres")
    private String email;

    @Pattern(regexp = "^[A-Za-z0-9]{3,30}$", message = "El usuario debe ser alfanumérico, max 30 caracteres")
    private String usuario;

    // Contraseña opcional: si se envía, debe cumplir tamaño mínimo
    @Size(min = 8, message = "La contraseña debe tener mínimo 8 caracteres")
    private String password;

    @Size(max = 150, message = "Negocio: máximo 150 caracteres")
    private String negocio;
}
