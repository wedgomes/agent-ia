package com.locacao.agente_ia;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

@Component
public class LocacaoTools {

    private final LocacaoService service;

    public LocacaoTools(LocacaoService service) {
        this.service = service;
    }

    @Tool("Verifica se há quantidade suficiente de um produto no estoque.")
    public String consultarEstoque(
            @P("Nome do produto") String nomeProduto,
            @P("Quantidade") int quantidade) {
        try {
            // BLINDAGEM: Se a IA mandar null ou nome vazio
            if (nomeProduto == null || nomeProduto.trim().isEmpty()) {
                return "ERRO: Não consegui identificar o nome do produto. Por favor, pergunte novamente informando o produto.";
            }
            return service.verificarDisponibilidade(nomeProduto, quantidade);
        } catch (Exception e) {
            // Captura qualquer erro Java e devolve como texto para a IA
            return "Ocorreu um erro técnico ao consultar o estoque: " + e.getMessage();
        }
    }

    @Tool("Calcula o orçamento estimado.")
    public String calcularOrcamento(
            @P("Nome do produto") String produto,
            @P("Número de dias") int dias,
            @P("Quantidade de itens") int quantidade) {
        try {
            double valor = service.calcularPrecoTotal(produto, dias, quantidade);
            return "O valor calculado é R$ " + valor;
        } catch (Exception e) {
            return "Erro ao calcular orçamento: " + e.getMessage();
        }
    }

    @Tool("Finaliza e grava o pedido de locação após confirmação expressa do usuário.")
    public String finalizarPedido(
            @P("Nome do produto") String produto,
            @P("Quantidade de itens") int quantidade,
            @P("Quantidade de dias") int dias,
            @P("Valor total calculado") double valorTotal) {

        try {
            Usuario usuarioLogado = TesteController.getUsuarioLogado();
            if (usuarioLogado == null) return "ERRO CRÍTICO: Usuário não identificado na sessão.";

            return service.realizarLocacao(usuarioLogado, produto, quantidade, dias, valorTotal);
        } catch (RuntimeException e) {
            return e.getMessage(); // Retorna o erro de validação (saldo, estoque)
        } catch (Exception e) {
            return "Erro técnico ao finalizar pedido: " + e.getMessage();
        }
    }
}