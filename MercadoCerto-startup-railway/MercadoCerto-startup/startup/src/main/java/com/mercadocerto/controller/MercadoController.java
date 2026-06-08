package com.mercadocerto.controller;

import com.mercadocerto.model.Mercado;
import com.mercadocerto.service.MercadoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/mercados")
@CrossOrigin(origins = "*")
public class MercadoController {

    private final MercadoService service;

    public MercadoController(MercadoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Mercado> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mercado> buscar(@PathVariable Integer id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Mercado> criar(@RequestBody Mercado mercado) {
        Mercado salvo = service.salvar(mercado);
        return ResponseEntity.created(URI.create("/api/mercados/" + salvo.getIdMercado()))
                .body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mercado> atualizar(@PathVariable Integer id, @RequestBody Mercado mercado) {
        try {
            Mercado atualizado = service.atualizar(id, mercado);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Integer id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/proximos")
    public List<Mercado> proximos(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(defaultValue = "5") double raioKm) {
        return service.buscarProximos(latitude, longitude, raioKm);
    }
}
