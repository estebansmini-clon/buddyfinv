package com.es.backendbuddyfinv.repository;

import com.es.backendbuddyfinv.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByUsuario(String usuario);
    boolean existsByEmail(String email);
    boolean existsByUsuarioOrEmail(String usuario, String email);
    Usuario findByUsuario(String usuario);
    Usuario findByEmail(String email);
}