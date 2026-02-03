package com.locacao.agente_ia;

import dev.langchain4j.agent.tool.P;
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

    @Tool("Finaliza o pedido de locação após confirmação do usuário.")
    public String finalizarPedido(
            @P("Nome do produto") String produto,
            @P("Valor total calculado") double valorTotal) {

        // ATENÇÃO: Em um sistema real, pegaríamos o usuário do SecurityContextHolder (Sessão).
        // Para simplificar aqui, vamos simular que a regra de negócio acessa o usuário "logado".
        // Vou usar um método estático auxiliar apenas para esse tutorial (não faça isso em prod!)
        Usuario usuarioLogado = TesteController.getUsuarioLogado();

        try {
            return service.validarCompra(usuarioLogado, valorTotal);
        } catch (RuntimeException e) {
            return e.getMessage(); // Retorna o erro amigável para a IA explicar ao usuário
        }
    }
}