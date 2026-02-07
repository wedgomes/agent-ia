package com.locacao.agente_ia;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeUsuario;
    private String produto;
    private int quantidade;
    private int dias;
    private double valorTotal;
    private LocalDateTime dataPedido;

    public Pedido() {}

    public Pedido(String nomeUsuario, String produto, int quantidade, int dias, double valorTotal) {
        this.nomeUsuario = nomeUsuario;
        this.produto = produto;
        this.quantidade = quantidade;
        this.dias = dias;
        this.valorTotal = valorTotal;
        this.dataPedido = LocalDateTime.now();
    }

    // Getter para o ID (para mostrarmos na mensagem final)
    public Long getId() { return id; }
}