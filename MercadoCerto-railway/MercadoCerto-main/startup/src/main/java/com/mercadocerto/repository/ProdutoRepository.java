package com.mercadocerto.repository;

import com.mercadocerto.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    // métodos CRUD padrão disponíveis
}
