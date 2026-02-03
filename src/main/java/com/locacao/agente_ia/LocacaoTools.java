package com.locacao.agente_ia;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

@Component // Indica que o Spring gerencia essa classe
public class LocacaoTools {

    private final LocacaoService service;

    public LocacaoTools(LocacaoService service) {
        this.service = service;
    }

    @Tool("Verifica se um produto está disponível para locação no estoque.")
    public String consultarEstoque(String nomeProduto) {
        return service.verificarDisponibilidade(nomeProduto);
    }

    @Tool("Calcula o valor total do orçamento baseado no produto, dias e quantidade.")
    public double calcularOrcamento(String nomeProduto, int dias, int quantidade) {
        return service.calcularPrecoTotal(nomeProduto, dias, quantidade);
    }
}