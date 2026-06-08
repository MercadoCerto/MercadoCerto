package com.mercadocerto.service;

import com.mercadocerto.model.Produto;
import com.mercadocerto.repository.ProdutoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepo;

    @Value("${uploads.path:uploads}")
    private String uploadsPath;

    public ProdutoService(ProdutoRepository produtoRepo) {
        this.produtoRepo = produtoRepo;
    }

    public Produto salvarProduto(Produto produto, MultipartFile imagem) throws IOException {

    if (produto.getIdMercado() == null) {
        throw new IllegalArgumentException("O mercado é obrigatório.");
    }

    if (imagem != null && !imagem.isEmpty()) {
        String nomeImagem = salvarImagemNoDisco(imagem);
        produto.setImagem(nomeImagem);
    }

    if (produto.getPreco() == null) {
        throw new IllegalArgumentException("Preço é obrigatório.");
    }

    return produtoRepo.save(produto);
}


    private String salvarImagemNoDisco(MultipartFile imagem) throws IOException {

        Path dir = Paths.get(uploadsPath);

        if (!Files.exists(dir)) {
            Files.createDirectories(dir);
        }

        String original = imagem.getOriginalFilename();
        String nomeSeguro = System.currentTimeMillis() + "_" +
                (original != null ? original.replaceAll("\\s+", "_") : "img");

        Path destino = dir.resolve(nomeSeguro);
        Files.copy(imagem.getInputStream(), destino);

        return nomeSeguro;
    }
}
