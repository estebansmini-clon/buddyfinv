package com.es.backendbuddyfinv.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.es.backendbuddyfinv.dto.RecoverUsernameRequest;
import com.es.backendbuddyfinv.service.impl.UsernameRecoveryService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class UsernameRecoveryController {
    
    private UsernameRecoveryService usernameRecoveryService;

    public UsernameRecoveryController(UsernameRecoveryService usernameRecoveryService){
        this.usernameRecoveryService= usernameRecoveryService;
    }

    @PostMapping("/recover-username")
    public ResponseEntity<?> recoverUsername(@RequestBody RecoverUsernameRequest request) {
        usernameRecoveryService.processRecovery(request.getEmail());

        return ResponseEntity.ok().body("Si el correo existe, se enviará la información de usuario al correo registrado.");
    }
}
