package com.locacao.agente_ia;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Map;

@Service // Indica que isso é uma classe de negócio do Spring
public class LocacaoService {

    private final ProdutoRepository produtoRepository;
    private final PedidoRepository pedidoRepository;

    // Injeção de dependência do repositório
    public LocacaoService(ProdutoRepository produtoRepository, PedidoRepository pedidoRepository) {
        this.produtoRepository = produtoRepository;
        this.pedidoRepository = pedidoRepository;
    }

    public String realizarLocacao(Usuario usuario, String nomeProduto, int quantidade, int dias, double valorTotal) {
        System.out.println("💾 DB: Tentando efetivar locação para " + usuario.nome());

        // 1. Validação de Segurança (Mantendo o que já fizemos)
        if (valorTotal > usuario.limiteAprovacao()) {
            throw new RuntimeException("BLOQUEADO: O valor R$ " + valorTotal + " excede o limite de R$ " + usuario.limiteAprovacao());
        }

        // 2. Validação de Estoque (Garantia final antes de salvar)
        Produto produto = produtoRepository.findByNomeContainingIgnoreCase(nomeProduto)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (produto.getQuantidadeEstoque() < quantidade) {
            throw new RuntimeException("ERRO: Estoque insuficiente no momento da gravação.");
        }

        // 3. ATUALIZA O ESTOQUE (A mágica acontece aqui)
        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidade);
        produtoRepository.save(produto); // UPDATE produto SET estoque = ...

        // 4. SALVA O PEDIDO
        Pedido pedido = new Pedido(usuario.nome(), nomeProduto, quantidade, dias, valorTotal);
        pedidoRepository.save(pedido);

        return "SUCESSO: Pedido #" + pedido.getId() + " confirmado! Estoque atualizado.";
    }

    public String verificarDisponibilidade(String nomeProduto, int quantidadeDesejada) {
        System.out.println("🔧 DB: Buscando produto " + nomeProduto);

        Optional<Produto> produtoOpt = produtoRepository.findByNomeContainingIgnoreCase(nomeProduto);

        if (produtoOpt.isEmpty()) {
            return "Produto não encontrado em nosso catálogo.";
        }

        Produto produto = produtoOpt.get();
        if (produto.getQuantidadeEstoque() >= quantidadeDesejada) {
            return "Disponível! Temos " + produto.getQuantidadeEstoque() + " unidades em estoque.";
        } else {
            return "Estoque insuficiente. Temos apenas " + produto.getQuantidadeEstoque() + " unidades.";
        }
    }

    public double calcularPrecoTotal(String nomeProduto, int dias, int quantidade) {
        Optional<Produto> produtoOpt = produtoRepository.findByNomeContainingIgnoreCase(nomeProduto);

        if (produtoOpt.isEmpty()) {
            throw new IllegalArgumentException("Produto não encontrado: " + nomeProduto);
        }

        return produtoOpt.get().getPrecoDiaria() * dias * quantidade;
    }
}