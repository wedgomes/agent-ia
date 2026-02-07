package com.locacao.agente_ia;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// Ao estender JpaRepository, ganhamos: save, findAll, findById, delete...
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // Méto do mágico: O Spring cria o SQL "SELECT * FROM produto WHERE nome = ?"
    Optional<Produto> findByNomeIgnoreCase(String nome);

    // Busca por parte do nome (LIKE %nome%) ignorando maiúsculas/minúsculas
    Optional<Produto> findByNomeContainingIgnoreCase(String nome);
}