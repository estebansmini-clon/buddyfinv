package com.es.backendbuddyfinv.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.es.backendbuddyfinv.model.Usuario;
import com.es.backendbuddyfinv.repository.UsuarioRepository;

@Service
public class UsernameRecoveryService {
    private final UsuarioRepository usuarioRepository;
    private final EmailService emailService;

    public UsernameRecoveryService(UsuarioRepository usuarioRepository, EmailService emailService){
        this.usuarioRepository=usuarioRepository;
        this.emailService=emailService;
    }

    public void processRecovery(String email){
        Optional<Usuario> userOpt = usuarioRepository.findByEmail(email);
        
        if (userOpt.isPresent()) {
            Usuario usuario = userOpt.get();

            emailService.sendUsernameRecoveryEmail(
                usuario.getEmail(),
                usuario.getUsuario()
            );
        }

    }
}
