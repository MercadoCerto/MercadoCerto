package com.mercadocerto.service;

import com.mercadocerto.model.Mercado;
import com.mercadocerto.repository.MercadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MercadoService {

    private final MercadoRepository repo;

    public MercadoService(MercadoRepository repo) {
        this.repo = repo;
    }

    public List<Mercado> listarTodos() {
        return repo.findAll();
    }

    public Optional<Mercado> buscarPorId(Integer id) {
        return repo.findById(id);
    }

    public Mercado salvar(Mercado mercado) {
        return repo.save(mercado);
    }

   public Mercado atualizar(Integer id, Mercado novo) {
    return repo.findById(id).map(m -> {
        m.setNomeMercado(novo.getNomeMercado());
        m.setLatitude(novo.getLatitude());
        m.setLongitude(novo.getLongitude());
        return repo.save(m);
    }).orElseThrow(() -> new RuntimeException("Mercado não encontrado: " + id));
}


    public void remover(Integer id) {
        repo.deleteById(id);
    }

    public List<Mercado> buscarProximos(double lat, double lng, double raioKm) {
    return repo.buscarPorProximidade(lat, lng, raioKm);
}
}
