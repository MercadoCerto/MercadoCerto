package com.mercadocerto.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "produto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto")
    private Integer idProduto;

    @Column(name = "nome_produto")
    private String nomeProduto;

    private String marca;
    private String categoria;

    @Column(name = "codigo_barras")
    private String codigoBarras;

    private String imagem;

    private Double preco;

    private String validade;

    @Column(name = "id_mercado")
    private Integer idMercado;
}
