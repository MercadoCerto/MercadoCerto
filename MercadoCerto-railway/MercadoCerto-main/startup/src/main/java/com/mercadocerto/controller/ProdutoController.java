package com.mercadocerto.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadocerto.model.Produto;
import com.mercadocerto.repository.ProdutoRepository;
import com.mercadocerto.service.ProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@CrossOrigin(origins = "*")
public class ProdutoController {

    private final ProdutoService produtoService;
    private final ProdutoRepository produtoRepo;
    private final ObjectMapper objectMapper;

    public ProdutoController(
            ProdutoService produtoService,
            ProdutoRepository produtoRepo,
            ObjectMapper objectMapper) {
        this.produtoService = produtoService;
        this.produtoRepo = produtoRepo;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public List<Produto> listarTodos() {
        return produtoRepo.findAll();
    }

    @PostMapping(
        value = "/cadastrar",
        consumes = {"multipart/form-data"}
)
public ResponseEntity<?> cadastrar(
        @RequestPart("produto") String produtoJson,
        @RequestPart("idMercado") Integer idMercado,
        @RequestPart(value = "imagem", required = false) MultipartFile imagem
) throws Exception {

    Produto produto = objectMapper.readValue(produtoJson, Produto.class);

    // define o mercado no produto
    produto.setIdMercado(idMercado);

    Produto salvo = produtoService.salvarProduto(produto, imagem);

    return ResponseEntity.ok(salvo);
}

}
