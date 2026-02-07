package com.locacao.agente_ia;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CargaDados {

    @Bean
    CommandLineRunner iniciarBanco(ProdutoRepository repository) {
        return args -> {
            repository.save(new Produto("Macbook Pro", 300.0, 5)); // Apenas 5 no estoque!
            repository.save(new Produto("Notebook Gamer", 250.0, 20));
            repository.save(new Produto("Monitor 4k", 50.0, 10));

            System.out.println("✅ Banco de dados carregado com sucesso!");
        };
    }
}