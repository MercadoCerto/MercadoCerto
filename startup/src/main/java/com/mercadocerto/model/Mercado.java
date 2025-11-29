package com.mercadocerto.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mercado")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class  Mercado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mercado")
    private Integer idMercado;

    @Column(name = "nome_mercado")
    private String nomeMercado;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;
}
