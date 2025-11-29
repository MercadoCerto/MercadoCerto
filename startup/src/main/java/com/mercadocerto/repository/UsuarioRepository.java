package com.mercadocerto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mercadocerto.model.Usuario;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByLogin(String login);
}
