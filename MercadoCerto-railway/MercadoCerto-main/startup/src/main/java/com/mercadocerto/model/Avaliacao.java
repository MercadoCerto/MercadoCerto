package com.mercadocerto.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "avaliacao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_avaliacao")
    private Integer idAvaliacao;

    @Column(name = "id_mercado")
    private Integer idMercado;

    @Column(name = "id_usuario")
    private Integer idUsuario;

    // seu dump tem nota int(11) CHECK (nota between 1 and 5)
    @Column(name = "nota")
    private Integer nota;

    @Column(name = "comentario", columnDefinition = "text")
    private String comentario;

    @Column(name = "data_avaliacao")
    private LocalDateTime dataAvaliacao;
}
