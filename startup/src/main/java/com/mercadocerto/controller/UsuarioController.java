package com.mercadocerto.controller;

import com.mercadocerto.model.Usuario;
import com.mercadocerto.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")  // Permite requests do frontend
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Cadastro
    @PostMapping("/register")
    public ResponseEntity<?> cadastrar(@RequestBody Usuario usuario) {
        Usuario cadastrado = usuarioService.cadastrar(usuario);
        return ResponseEntity.ok(cadastrado);
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> dados) {
        String login = dados.get("login");
        String senha = dados.get("senha");

        Optional<Usuario> user = usuarioService.buscarPorLogin(login);

        if (user.isPresent() && passwordEncoder.matches(senha, user.get().getSenha())) {
            return ResponseEntity.ok(user.get());
        }

        return ResponseEntity.status(401).body("Credenciais inválidas");
    }

    // Listar
    @GetMapping
    public List<Usuario> listar() {
        return usuarioService.listarTodos();
    }

    // Buscar por login
    @GetMapping("/login/{login}")
    public Optional<Usuario> buscarPorLogin(@PathVariable String login) {
        return usuarioService.buscarPorLogin(login);
    }

    // Buscar por email
    @GetMapping("/email/{email}")
    public Optional<Usuario> buscarPorEmail(@PathVariable String email) {
        return usuarioService.buscarPorEmail(email);
    }
}
