package com.locacao.agente_ia;

import org.springframework.stereotype.Service;
import java.util.Map;

@Service // Indica que isso é uma classe de negócio do Spring
public class LocacaoService {

    // Simula nosso banco de dados de produtos e preços
    private static final Map<String, Double> PRECOS = Map.of(
            "notebook gamer", 250.0,
            "macbook pro", 300.0,
            "projetor 4k", 100.0
    );

    public String verificarDisponibilidade(String produto) {
        System.out.println("🔧 SISTEMA: Verificando estoque para: " + produto);

        // Simples verificação: se está no mapa, temos estoque
        if (PRECOS.containsKey(produto.toLowerCase())) {
            return "Disponível";
        }
        return "Indisponível";
    }

    public double calcularPrecoTotal(String produto, int dias, int quantidade) {
        System.out.println("🔧 SISTEMA: Calculando orçamento...");

        Double precoDiaria = PRECOS.get(produto.toLowerCase());
        if (precoDiaria == null) {
            throw new IllegalArgumentException("Produto não cadastrado: " + produto);
        }

        return precoDiaria * dias * quantidade;
    }
}