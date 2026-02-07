package com.locacao.agente_ia;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity // Isso vira uma tabela "produto" no banco
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private double precoDiaria;
    private int quantidadeEstoque;

    // Construtor vazio (obrigatório pro JPA)
    public Produto() {}

    // Construtor para facilitar nossa vida
    public Produto(String nome, double precoDiaria, int quantidadeEstoque) {
        this.nome = nome;
        this.precoDiaria = precoDiaria;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    // Getters
    public String getNome() { return nome; }
    public double getPrecoDiaria() { return precoDiaria; }
    public int getQuantidadeEstoque() { return quantidadeEstoque; }

    // Setters (para atualizar estoque depois)
    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }
}