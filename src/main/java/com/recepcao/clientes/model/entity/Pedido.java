package com.recepcao.clientes.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "pedido")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String numeroControle;

    private LocalDate dataCadastro;

    private String nome;

    private BigDecimal valor;

    private Integer quantidade;

    private BigDecimal valorTotal;

    @Column(nullable = false)
    private Integer codigoCliente;
}
